package com.example.stepsDefinition.ui;

import com.example.playwrightManager.PlaywrightManager;
import com.example.configurations.ConfigLoader;
import com.example.commonMethods.CommonMethods;
import com.example.enums.PageURL;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;


public class CommonStepDefinition {
    private static final Logger logger = LogManager.getRootLogger();
    PlaywrightManager driver = PlaywrightManager.getInstance();
    CommonMethods commonMethods = new CommonMethods();
    SoftAssert softAssert = new SoftAssert();


    @Given("Navigate to {string} page")
    public void navigateToPage(String page) {
        String label = commonMethods.refactoredUserFriendlyName(page);
        driver.navigateTo(String.format("%s%s", ConfigLoader.getProperty("base.url"), PageURL.valueOf(label.toUpperCase()).getPath()));

        logger.info("Navigate to {} page", page);
    }

    @And("Check that redirect to {string} page")
    public void redirectToPage(String page) {
        String label = commonMethods.refactoredUserFriendlyName(page);
        String expectedURLpath = String.format("%s%s", ConfigLoader.getProperty("base.url"), PageURL.valueOf(label.toUpperCase()).getPath());
        String actualURLpath = driver.getPage().url();

        softAssert.assertEquals(expectedURLpath, actualURLpath);
        softAssert.assertAll();

        logger.info("Redirect to {} page", page);
    }
}
