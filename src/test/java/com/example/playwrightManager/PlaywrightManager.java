
package com.example.playwrightManager;

import com.microsoft.playwright.*;

public class PlaywrightManager {
    private static final ThreadLocal<PlaywrightManager> instance = ThreadLocal.withInitial(PlaywrightManager::new);
    private static final ThreadLocal<Playwright> playwright = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<Browser> browser = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<BrowserContext> context = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<Page> page = ThreadLocal.withInitial(() -> null);

    private PlaywrightManager() {
    }

    public void LaunchBrowser(String browserName, boolean headless) {
        if (playwright.get() == null) {
            playwright.set(Playwright.create());
        }

        switch (browserName) {
            case "FIREFOX":
                browser.set(playwright.get().firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "SAFARI":
                browser.set(playwright.get().webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "EDGE":
                browser.set(playwright.get().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(headless)));
                break;
            case "CHROME":
            default:
                browser.set(playwright.get().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless)));
                break;
        }

        context.set(browser.get().newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080)));
        page.set(context.get().newPage());
    }

    public static PlaywrightManager getInstance() {
        return instance.get();
    }

    public Page getPage() {
        return page.get();
    }

    public void navigateTo(String url) {
        if (page.get() != null) {
            page.get().navigate(url);
        }
    }

    public void close() {
        if (browser.get() != null) {
            browser.get().close();
        }
        if (playwright.get() != null) {
            playwright.get().close();
        }

        page.remove();
        context.remove();
        browser.remove();
        playwright.remove();
        instance.remove(); // important in multithreaded runs
    }

    public Locator findElement(String locator) {
        return page.get().locator(locator);
    }
}
