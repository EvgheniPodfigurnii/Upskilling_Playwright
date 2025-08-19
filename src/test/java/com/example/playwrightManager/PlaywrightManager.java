package com.example.playwrightManager;

import com.microsoft.playwright.*;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/** Manages Playwright and browser resources per thread. */
public class PlaywrightManager {
    private static final Set<PlaywrightManager> threads =
            Collections.newSetFromMap(new ConcurrentHashMap<>());

    private static final ThreadLocal<PlaywrightManager> instance =
            ThreadLocal.withInitial(() -> {
                PlaywrightManager manager = new PlaywrightManager();
                threads.add(manager);
                return manager;
            });

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private PlaywrightManager() {
    }

    public static PlaywrightManager getInstance() {
        return instance.get();
    }

    /** Launches a browser of the given type; creates context and sets viewport. */
    public void launchBrowser(String browserName, boolean headless) {
        if (playwright == null) {
            playwright = Playwright.create();
        }

        if (browser == null) {
            switch (browserName.toUpperCase()) {
                case "FIREFOX":
                    browser = playwright.firefox().launch(
                            new BrowserType.LaunchOptions().setHeadless(headless));
                    break;
                case "SAFARI":
                    browser = playwright.webkit().launch(
                            new BrowserType.LaunchOptions().setHeadless(headless));
                    break;
                case "EDGE":
                    browser = playwright.chromium().launch(
                            new BrowserType.LaunchOptions()
                                    .setChannel("msedge").setHeadless(headless));
                    break;
                case "CHROME":
                default:
                    browser = playwright.chromium().launch(
                            new BrowserType.LaunchOptions()
                                    .setChannel("chrome").setHeadless(headless));
                    break;
            }
            context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
        }
    }

    /** Opens a new page in the current context. */
    public void openNewTab() {
        if (context == null) {
            throw new IllegalStateException("Browser context has not been initialised. Call launchBrowser() first.");
        }
        page = context.newPage();
    }

    /** Clears cookies in the current context. */
    public void cleanupScenario() {
        if (context != null) {
            context.clearCookies();
        }
    }

    public Page getPage() {
        return page;
    }

    public void navigateTo(String url) {
        if (page != null) {
            page.navigate(url);
        }
    }

    /**
     * Closes the Playwright resources owned by this instance. This should be
     * called from the same thread that created them (for example, in an @After hook).
     */
    public void close() {
        if (page != null) {
            try {
                page.close();
            } catch (Exception e) {
                System.err.println("Failed to close page: " + e.getMessage());
            } finally {
                page = null;
            }
        }
        if (context != null) {
            try {
                context.close();
            } catch (Exception e) {
                System.err.println("Failed to close context: " + e.getMessage());
            } finally {
                context = null;
            }
        }
        if (browser != null) {
            try {
                browser.close();
            } catch (Exception e) {
                System.err.println("Failed to close browser: " + e.getMessage());
            } finally {
                browser = null;
            }
        }
        if (playwright != null) {
            try {
                playwright.close();
            } catch (Exception e) {
                System.err.println("Failed to close Playwright: " + e.getMessage());
            } finally {
                playwright = null;
            }
        }
        // Remove this thread from the global set and ThreadLocal
        threads.remove(this);
        instance.remove();
    }

    /** Closes all PlaywrightManager instances across threads. */
    public void closeAll() {
        for (PlaywrightManager manager : threads.toArray(new PlaywrightManager[0])) {
            manager.close();
        }
        threads.clear();
    }

    /** Finds a locator on the current page. */
    public Locator findElement(String locator) {
        return page.locator(locator);
    }
}
