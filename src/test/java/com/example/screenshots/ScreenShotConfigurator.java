package com.example.screenshots;

import com.example.playwrightManager.PlaywrightManager;
import com.example.utils.ScenarioContext;
import com.microsoft.playwright.Page;
import java.nio.file.Paths;

public class ScreenShotConfigurator {
    PlaywrightManager playwrightManager = PlaywrightManager.getInstance();
    ScenarioContext scenarioContext = ScenarioContext.getInstance();

//    public void takeScreenshot(Scenario scenario) {
//        try {
//            String folderPath = scenarioContext.get("LogPath");
//            String screenshotPath = folderPath + "/screenshot.png";
//
//            Page page = playwrightManager.getPage(); // assuming you set it earlier
//
//            page.screenshot(new Page.ScreenshotOptions()
//                    .setPath(Paths.get(screenshotPath))
//                    .setFullPage(true));
//
//            scenario.attach(Files.readAllBytes(Path.of(screenshotPath)), "image/png", "Screenshot");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public void takeScreenshot() {
        try {
            Page page = playwrightManager.getPage();
            if (page == null || page.isClosed()) {
                System.out.println("Cannot take screenshot: Page is null or closed.");
                return;
            }

            // Wait for something meaningful to appear on the page
//            try {
//                page.waitForSelector("body", new Page.WaitForSelectorOptions().setTimeout(5000));
//            } catch (Exception e) {
//                System.out.println("⚠️ No body found before screenshot: " + e.getMessage());
//            }

            String folderPath = scenarioContext.get("LogPath");
            String screenshotPath = folderPath + "/screenshot_" + System.currentTimeMillis() + ".png";

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(screenshotPath))
                    .setFullPage(true));

            System.out.println("Screenshot saved at: " + screenshotPath);
            System.out.println("URL: " + page.url());
            System.out.println("Title: " + page.title());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
