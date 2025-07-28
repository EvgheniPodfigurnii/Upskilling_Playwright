package com.example.screenshots;

import com.example.playwrightManager.PlaywrightManager;
import com.example.utils.ScenarioContext;
import com.microsoft.playwright.Page;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class ScreenShotConfigurator {
    PlaywrightManager playwrightManager = PlaywrightManager.getInstance();
    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    private final AtomicInteger index = new AtomicInteger();

    public void takeScreenshot() {
        playwrightManager.getPage().screenshot(
                new Page.ScreenshotOptions().setPath(Paths.get(String.format("%s/%s", scenarioContext.get("LogPath"), String.format("screenshotIndex_%s.png", index.incrementAndGet())))).setFullPage(true));
    }

    public void takeScreenshot(Scenario scenario, boolean attachToAllure) {
        byte[] screenshot = playwrightManager.getPage().screenshot(
                new Page.ScreenshotOptions().setPath(Paths.get(String.format("%s/%s", scenarioContext.get("LogPath"), String.format("screenshotGetLine_%s.png", scenario.getLine())))).setFullPage(true));

        if (attachToAllure) {
            Allure.addAttachment(String.format("ScreenshotGetLine_%s - %s",scenario.getLine(), scenario.getName()), new ByteArrayInputStream(screenshot));
        }
    }
}
