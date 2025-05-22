package com.example.hooks;

import com.example.logging.LogConfigurator;
import com.example.logging.LoggingContext;
import com.example.playwrightManager.PlaywrightManager;
import com.example.screenshots.ScreenShotConfigurator;
import com.example.utils.ScenarioContext;
import com.microsoft.playwright.Page;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExecutionHook {
    ScreenShotConfigurator screenShotConfigurator = new ScreenShotConfigurator();

    @Before
    public void setUp() {
        PlaywrightManager.getInstance();
        ScenarioContext.getInstance();
    }


    @Before
    public void setup(Scenario scenario) {
        String rawUri = scenario.getUri().toString();
        String featureName = new File(rawUri).getName().replace(".feature", "").replaceAll("[^a-zA-Z0-9.-]", "_");
        String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9.-]", "_");

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String time = now.format(DateTimeFormatter.ofPattern("HH_mm_ss"));

        String folderPath = "logs/" + date + "/" + featureName + "/" + time + "_" + scenarioName;
        new File(folderPath).mkdirs();
        String logFilePath = folderPath + "/test.log";

        LoggingContext.setLogFilePath(logFilePath);
        LogConfigurator.configureLogger(logFilePath);

        ScenarioContext.getInstance().set("LogPath", folderPath);
    }


    @After(order = 2)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            screenShotConfigurator.takeScreenshot();
        }
    }

    @After(order = 1)
    public void tearDown() {
        LoggingContext.remove();
        PlaywrightManager.getInstance().close();
    }
}
