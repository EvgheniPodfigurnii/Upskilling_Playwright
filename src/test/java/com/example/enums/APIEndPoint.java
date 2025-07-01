package com.example.enums;

public enum APIEndPoint {
    LOGIN("api/verifyLogin"),
    CREATE("api/createAccount"),
    DELETE("api/deleteAccount"),
    UPDATE("api/updateAccount"),
    GET_USER_DETAILS("api/getUserDetailByEmail"),
    PRODUCT_LIST("api/productsList");

    private final String key;

    APIEndPoint(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static String getAPIEndPoint(APIEndPoint apiEndPoint) {
        return apiEndPoint.getKey();
    }
}
