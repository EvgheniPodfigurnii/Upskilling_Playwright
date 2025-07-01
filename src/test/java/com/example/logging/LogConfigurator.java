package com.example.logging;

import com.example.utils.ScenarioContext;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogConfigurator {
    static ScenarioContext scenarioContext = ScenarioContext.getInstance();

    public static void configureLogger(String logFilePath) {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();

        // Clean up previous scenario logger if any
        LoggerConfig rootLogger = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        for (Appender appender : rootLogger.getAppenders().values()) {
            appender.stop();
            rootLogger.removeAppender(appender.getName());
        }

        Layout<String> layout = PatternLayout.newBuilder()
                .withConfiguration(config)
                .withPattern("[%d{HH:mm:ss}] [%p] %m%n")
                .build();

        Appender appender = FileAppender.newBuilder()
                .withFileName(logFilePath)
                .withName("ScenarioFileAppender")
                .withLayout(layout)
                .withAppend(false)
                .withBufferedIo(true)
                .setConfiguration(config)
                .build();

        appender.start();
        config.addAppender(appender);

        rootLogger.addAppender(appender, Level.INFO, null);
        context.updateLoggers();
    }

    public static void getLoggingPath(Scenario scenario, String typeOfTest) {
            LocalDateTime now = LocalDateTime.now();
            String date = now.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            String time = now.format(DateTimeFormatter.ofPattern("HH_mm_ss"));

        String rawUri = scenario.getUri().toString();
        String featureName = new File(rawUri).getName().replace(".feature", "").replaceAll("[^a-zA-Z0-9.-]", "_");
        String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9.-]", "_");

        String folderPath = String.format("target/logs/%s/%s/%s/%s/%s", date, typeOfTest, time, featureName, scenarioName);
        new File(folderPath).mkdirs();
        String logFilePath = folderPath + "/test.log";

        LogConfigurator.configureLogger(logFilePath);

        scenarioContext.set("LogPath", folderPath);
    }
}
