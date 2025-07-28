package com.example.logging;

import com.example.utils.ScenarioContext;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.ThreadContext;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogConfigurator {
    private static final String date;
    private static final String time;
    private final ScenarioContext scenarioContext;

    public LogConfigurator() {
        this.scenarioContext = ScenarioContext.getInstance();
    }

    static {
        LocalDateTime now = LocalDateTime.now();
        date = now.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        time = now.format(DateTimeFormatter.ofPattern("HH-mm-ss"));
    }

    public void setupLogging(Scenario scenario, String typeOfTest) {
        String rawUri = scenario.getUri().toString();
        String featureName = new File(rawUri).getName().replace(".feature", "").replaceAll("[^a-zA-Z0-9.-]", "_");
        String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9.-]", "_");

        String folderPath = String.format("target/logs/%s/%s/%s/%s/%s", date, time, typeOfTest, featureName, scenarioName);
        new File(folderPath).mkdirs();

        String logFileName = folderPath + String.format("/testGetLine_%s.log", scenario.getLine());

        // Set MDC variable
        ThreadContext.put("logFilePath", logFileName);
        scenarioContext.set("LogPath", folderPath);
    }

    public void clearContext() {
        ThreadContext.clearAll();
    }
}
