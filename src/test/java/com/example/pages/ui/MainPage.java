package com.example.pages.ui;

import com.example.playwrightManager.PlaywrightManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.awaitility.Awaitility;

import java.time.Duration;

public class MainPage {
    PlaywrightManager driver;
    private Locator header;
    private Locator slider;
    private Locator footer;

    public MainPage(Page page) {
        this.header = page.locator("#header");
        this.slider = page.locator("#slider");
        this.footer = page.locator("#footer");
    }

    public MainPage(PlaywrightManager driverManager) {
        this.driver = driverManager;
    }

//    public void checkThatPageIsVisible() {
//        Awaitility.await()
//                .atMost(Duration.ofSeconds(10))
//                .pollInterval(Duration.ofMillis(500))
//                .until(() ->
//                        header..isDisplayed() &&
//                        slider.isDisplayed() &&
//                        footer.isDisplayed()
//                );
//    }
}
