package com.example.enums;

public enum BrowserEnum {
    EDGE("EDGE"),
    CHROME("CHROME"),
    SAFARI("SAFARI"),
    FIREFOX("FIREFOX");

    private final String key;

    BrowserEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
