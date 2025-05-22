package com.example.utils;

import com.example.configurations.ConfigLoader;
import java.util.HashMap;
import java.util.Map;


public class ScenarioContext {
    private static ScenarioContext scenarioContext;
    private final Map<String, String> context = new HashMap<>();


    private ScenarioContext() {
        context.put("username", ConfigLoader.getProperty("username"));
        context.put("email", ConfigLoader.getProperty("email"));
        context.put("password", ConfigLoader.getProperty("password"));
    }

    public static ScenarioContext getInstance() {
        if (scenarioContext == null) {
            scenarioContext = new ScenarioContext();
        }

        return scenarioContext;
    }

    public void set(String key, String value) {
        context.put(key, value);
    }

    public String get(String key) {
        return context.get(key);
    }
}
