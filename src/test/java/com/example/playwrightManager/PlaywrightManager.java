package com.example.playwrightManager;

import com.example.configurations.ConfigLoader;
import com.microsoft.playwright.*;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/** Per-thread Playwright manager:
 *  - 1 Browser + 1 Context per thread (persistent)
 *  - 1 new Page (tab) per scenario; tabs stay open
 *  - Rotate THIS thread's browser when tab-cap is reached
 *  - Safe idle-only global close; shutdown hook as a fallback
 */
public class PlaywrightManager {

    // ---- Global registry of per-thread managers (for optional global cleanup) ----
    private static final Set<PlaywrightManager> threads =
            Collections.newSetFromMap(new ConcurrentHashMap<>());

    // Thread-local manager instance
    private static final ThreadLocal<PlaywrightManager> instance =
            ThreadLocal.withInitial(() -> {
                PlaywrightManager m = new PlaywrightManager();
                threads.add(m);
                return m;
            });

    private static final int maxPagesPerBrowser = Integer.parseInt(ConfigLoader.getProperty("max.pages.per.browser"));
    private final ReentrantLock lock = new ReentrantLock();
    private Playwright playwright;     // per-thread
    private Browser browser;           // per-thread
    private BrowserContext context;    // per-thread (persistent)
    private Page page;                 // last opened page reference (current scenario)

    // Counters
    private int pagesCreatedSinceLaunch = 0;  // total tabs created on current browser
    private int inFlightScenarios = 0;        // scenarios currently running on THIS thread

    private PlaywrightManager() {
    }

    public static PlaywrightManager getInstance() {
        return instance.get();
    }

    /** Launch the browser/context for THIS thread (no-op if already launched). */
    public void launchBrowser(String browserName, boolean headless) {
        lock.lock();
        try {
            if (playwright == null) {
                playwright = Playwright.create();
            }
            if (browser == null) {
                browser = doLaunch(browserName, headless);
                context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
                pagesCreatedSinceLaunch = 0;
            }
        } finally {
            lock.unlock();
        }
    }

    /** Mark THIS thread as having started a scenario (so we don't close while busy). */
    public void beginScenario() {
        lock.lock();
        try {
            inFlightScenarios++;
        } finally {
            lock.unlock();
        }
    }

    /** Open a NEW tab for the current scenario. The tab stays open after the scenario ends. */
    public void openNewTab() {
        lock.lock();
        try {
            page = context.newPage();
            pagesCreatedSinceLaunch++;
        } finally {
            lock.unlock();
        }
    }

    /** After each scenario: keep the tab open; clear cookies; rotate if cap reached; mark thread idle if done. */
    public void endScenarioDontCloseTab(String browserName, boolean headless) {
        lock.lock();
        try {
            // Drop the reference so step defs don't accidentally reuse a finished scenario's page.
            page = null;

            if (context != null) {
                try { context.clearCookies(); } catch (Exception ignored) {}
            }

            if (pagesCreatedSinceLaunch >= maxPagesPerBrowser) {
                rotateBrowser(browserName, headless); // per-thread rotation
            }
        } finally {
            lock.unlock();
            decrementInFlight();
        }
    }

    /** Get the current scenario's Page reference (only valid inside the scenario). */
    public Page getPage() {
        return page;
    }

    /** Convenience locator helper. */
    public Locator findElement(String locator) {
        return getPage().locator(locator);
    }

    public void navigateTo(String url) {
        Page p = getPage();
        if (p != null) p.navigate(url);
    }

    /** Force-close THIS thread's resources (use at end-of-run or shutdown). */
    public void close() {
        lock.lock();
        try {
            safeClose(page);    page = null;
            safeClose(context); context = null;
            safeClose(browser); browser = null;
            if (playwright != null) { try { playwright.close(); } catch (Exception ignored) {} playwright = null; }
            pagesCreatedSinceLaunch = 0;
        } finally {
            lock.unlock();
            threads.remove(this);
            instance.remove();
        }
    }

    /**
     * Close THIS thread only if idle (no running scenario). Returns true if closed.
     */
    private void closeIfIdle() {
        lock.lock();
        try {
            if (inFlightScenarios > 0) {
                return;
            }
            safeClose(page);    page = null;
            safeClose(context); context = null;
            safeClose(browser); browser = null;
            if (playwright != null) { try { playwright.close(); } catch (Exception ignored) {} playwright = null; }
            pagesCreatedSinceLaunch = 0;
        } finally {
            lock.unlock();
            threads.remove(this);
            instance.remove();
        }
    }

    /** Close all idle managers (won't block on threads that still have running scenarios). */
    public static void closeAllIdle() {
        for (PlaywrightManager m : threads.toArray(new PlaywrightManager[0])) {
            try { m.closeIfIdle(); } catch (Exception ignore) {}
        }
    }

    /** Force-close everything (use only at final JVM shutdown). */
    public static void closeAll() {
        for (PlaywrightManager m : threads.toArray(new PlaywrightManager[0])) {
            try { m.close(); } catch (Exception ignore) {}
        }
        threads.clear();
    }

    private void rotateBrowser(String browserName, boolean headless) {
        // Close THIS thread's browser+context and relaunch fresh with the same config
        safeClose(page);    page = null;
        safeClose(context); context = null;
        safeClose(browser); browser = null;

        browser = doLaunch(browserName, headless);
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
        pagesCreatedSinceLaunch = 0;
    }

    private Browser doLaunch(String browserName, boolean headless) {
        switch (browserName.toUpperCase()) {
            case "FIREFOX":
                return playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            case "SAFARI":
                return playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            case "EDGE":
                return playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(headless));
            case "CHROME":
            default:
                return playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless));
        }
    }

    private static void safeClose(AutoCloseable c) {
        if (c != null) {
            try { c.close(); } catch (Exception ignored) {}
        }
    }

    private void decrementInFlight() {
        lock.lock();
        try {
            inFlightScenarios = Math.max(0, inFlightScenarios - 1);
        } finally {
            lock.unlock();
        }
    }

    // JVM safety net: if anything was left open, close it at process exit.
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try { closeAll(); } catch (Throwable ignored) {}
        }, "pm-shutdown-hook"));
    }
}
