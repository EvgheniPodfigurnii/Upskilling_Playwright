
package com.example.playwrightManager;

import com.microsoft.playwright.*;

public class PlaywrightManager {
    private static PlaywrightManager instance;
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    private PlaywrightManager() {
    }

    public void LaunchBrowser(String browserName) {
        playwright = Playwright.create();

        switch (browserName) {
            case "FIREFOX":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "SAFARI":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "EDGE":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(false));
                break;
            case "CHROME":
            default:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
                break;
        }

        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
        page = context.newPage();
    }

    public static PlaywrightManager getInstance() {
        if (instance == null) {
            instance = new PlaywrightManager();
        }
        return instance;
    }

    public Page getPage() {
        return page;
    }

    public void navigateTo(String url) {
        page.navigate(url);
    }

    public void close() {
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
        instance = null; // <- Important to reset for next scenario
    }

    public Locator findElement(String locator) {
        return page.locator(locator);
    }
}
