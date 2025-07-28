package com.example.enums;

public enum BrowserEnum {
    EDGE("EDGE"),
    CHROME("CHROME"),
    SAFARI("SAFARI"),
    FIREFOX("FIREFOX"),
    HEADLESS_TRUE(true),
    HEADLESS_FALSE(false);

    private String key;
    private boolean booleanValue;

    BrowserEnum(String key) {
        this.key = key;
    }

    BrowserEnum(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getKey() {
        return key;
    }

    public boolean getBoolean() {
        return booleanValue;
    }
}
