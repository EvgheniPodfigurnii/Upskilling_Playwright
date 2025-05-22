package com.example.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class LogConfigurator {

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
}
