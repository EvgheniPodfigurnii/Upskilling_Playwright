package com.example.softAssertion;

import org.testng.asserts.SoftAssert;

public class SoftAssertion {
    private static final ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);

    public static SoftAssert get() {
        return softAssert.get();
    }
}
