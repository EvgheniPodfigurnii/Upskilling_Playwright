package com.example.hooks;

import com.example.logging.LogConfigurator;
import com.example.playwrightManager.PlaywrightManager;
import com.example.screenshots.ScreenShotConfigurator;
import com.example.utils.ScenarioContext;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExecutionHook {
    static String date = "";
    static String time = "";
    String typeOfTests = "";
    static boolean firstRun = true;

    @Before
    public void setUp(Scenario scenario) {
        if (scenario.getSourceTagNames().stream().anyMatch(tag -> tag.equalsIgnoreCase("@ui"))) {
            PlaywrightManager.getInstance();
            typeOfTests = "UI";
        } else {
            typeOfTests = "API";
        }

        ScenarioContext.getInstance();

        if (firstRun) {
            firstRun = false;
            LocalDateTime now = LocalDateTime.now();
            date = now.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            time = now.format(DateTimeFormatter.ofPattern("HH_mm_ss"));
        }

        String rawUri = scenario.getUri().toString();
        String featureName = new File(rawUri).getName().replace(".feature", "").replaceAll("[^a-zA-Z0-9.-]", "_");
        String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9.-]", "_");

        String folderPath = String.format("target/logs/%s/%s/%s/%s/%s", date, typeOfTests, time, featureName, scenarioName);
        new File(folderPath).mkdirs();
        String logFilePath = folderPath + "/test.log";

        LogConfigurator.configureLogger(logFilePath);

        ScenarioContext.getInstance().set("LogPath", folderPath);
    }


    @After(order = 2)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed() && scenario.getSourceTagNames().stream().anyMatch(tag -> tag.equalsIgnoreCase("@ui"))) {
            ScreenShotConfigurator screenShotConfigurator = new ScreenShotConfigurator();
            screenShotConfigurator.takeScreenshot();

            Allure.addAttachment("Failure Screenshot", new ByteArrayInputStream(screenShotConfigurator.takeScreenshotAndReturnByte()));
        }
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
        if (scenario.getSourceTagNames().stream().anyMatch(tag -> tag.equalsIgnoreCase("@ui"))) {
            PlaywrightManager.getInstance().close();
        }
    }
}
