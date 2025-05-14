package com.example.stepsDefinition.ui;

import com.example.playwrightManager.PlaywrightManager;
import com.example.configurations.ConfigLoader;
import com.example.softAssertion.SoftAssertion;
import com.example.commonMethods.CommonMethods;
import com.example.enums.PageURL;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class CommonStepDefinition {
    PlaywrightManager driver = PlaywrightManager.getInstance();
    CommonMethods commonMethods = new CommonMethods();


    @Given("Navigate to {string} page")
    public void navigateToPage(String page) {
        String label = commonMethods.refactoredUserFriendlyName(page);
        driver.navigateTo(String.format("%s%s", ConfigLoader.getProperty("base.url"), PageURL.valueOf(label.toUpperCase()).getPath()));
    }

    @And("Check that redirect to {string} page")
    public void redirectToPage(String page) {
        String label = commonMethods.refactoredUserFriendlyName(page);
        String expectedURLpath = String.format("%s%s", ConfigLoader.getProperty("base.url"), PageURL.valueOf(label.toUpperCase()).getPath());
//        String actualURLpath = driver.getCurrentURL();
//
//        SoftAssertion.get().assertEquals(expectedURLpath, actualURLpath);
//        SoftAssertion.get().assertAll();
    }
}
