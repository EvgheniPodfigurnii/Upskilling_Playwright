package com.example.logging;

public class LoggingContext {
    private static final ThreadLocal<String> logFilePath = new ThreadLocal<>();

    public static void setLogFilePath(String path) {
        logFilePath.set(path);
    }

    public static String getLogFilePath() {
        return logFilePath.get();
    }

    public static void remove() {
        logFilePath.remove();
    }
}
