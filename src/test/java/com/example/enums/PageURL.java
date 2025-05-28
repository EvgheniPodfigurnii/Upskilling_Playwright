package com.example.enums;

import java.util.HashMap;
import java.util.Map;

public enum PageURL {
    HOME(""),
    PRODUCTS("products"),
    CART("view_cart"),
    SIGNUP_LOGIN("login"),
    TEST_CASES("test_cases"),
    API_TESTING("api_list7"),
    VIDEO_TUTORIALS("https://www.youtube.com/c/AutomationExercise"),
    CONTACT_US("contact_us");

    private final String path;
    public static final Map<String, String> labelKeyHeaderLinks = new HashMap<>();
    public static final Map<String, String> keyLabelHeaderLinks = new HashMap<>();

    static {
        for (PageURL pageURL : values()) {
            labelKeyHeaderLinks.put(pageURL.name().toLowerCase(), pageURL.path.toLowerCase());
        }
    }

    static {
        for (PageURL pageURL : values()) {
            keyLabelHeaderLinks.put(pageURL.path.toLowerCase(), pageURL.name().toLowerCase());
        }
    }

    PageURL(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static String getLabelPageUrlList(String key) {
        return labelKeyHeaderLinks.get(key.toLowerCase());
    }
}