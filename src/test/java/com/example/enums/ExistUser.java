package com.example.enums;

public enum ExistUser {
    USERNAME("newuser8"),
    EMAIL("newemail8@newemail.com"),
    PASSWORD("newspass123!");

    private final String key;

    ExistUser(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
