package com.example.utils;

import java.util.HashMap;
import java.util.Map;


public class ScenarioContext {
    private static final ThreadLocal<ScenarioContext> scenarioContext = ThreadLocal.withInitial(ScenarioContext::new);
    private final Map<String, String> context = new HashMap<>();


    public static ScenarioContext getInstance() {
        return scenarioContext.get();
    }

    public void set(String key, String value) {
        context.put(key, value);
    }

    public String get(String key) {
        return context.get(key);
    }
}
