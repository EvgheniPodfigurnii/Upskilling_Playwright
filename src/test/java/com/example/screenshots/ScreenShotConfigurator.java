package com.example.screenshots;

import com.example.playwrightManager.PlaywrightManager;
import com.example.utils.ScenarioContext;
import com.microsoft.playwright.Page;

import java.nio.file.Paths;

public class ScreenShotConfigurator {
    PlaywrightManager playwrightManager = PlaywrightManager.getInstance();
    ScenarioContext scenarioContext = ScenarioContext.getInstance();

    public void takeScreenshot() {
        playwrightManager.getPage().screenshot(
                new Page.ScreenshotOptions().setPath(Paths.get(String.format("%s/%s", scenarioContext.get("LogPath"), String.format("screenshot%s.png", System.currentTimeMillis())))).setFullPage(true));
    }
}
