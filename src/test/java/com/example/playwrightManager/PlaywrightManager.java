package com.example.playwrightManager;

import com.example.configurations.ConfigLoader;
import com.microsoft.playwright.*;

public class PlaywrightManager {
    String browserName = ConfigLoader.getProperty("browser");

    private static PlaywrightManager instance;
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;


    private PlaywrightManager() {
        playwright = Playwright.create();

        switch (browserName.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "safari":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "chrome":
            case "edge":
            default:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
        }

        context = browser.newContext();
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
        context.close();
        browser.close();
        playwright.close();
    }

    public Locator findElement(String locator) {
        return page.locator(locator);
        //        return browser.newPage().locator(locator);
    }

}
