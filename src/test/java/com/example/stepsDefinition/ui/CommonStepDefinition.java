package com.example.stepsDefinition.ui;

import com.example.playwrightManager.PlaywrightManager;
import com.example.configurations.ConfigLoader;
import com.example.commonMethods.CommonMethods;
import com.example.enums.PageURL;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;


public class CommonStepDefinition {
    private static final Logger logger = LogManager.getRootLogger();
    PlaywrightManager driver = PlaywrightManager.getInstance();
    CommonMethods commonMethods = new CommonMethods();

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

        if (actualURLpath.equals(expectedURLpath)) {
            logger.info("Redirect to {} page", page);
        } else {
            assertThat(actualURLpath)
                    .withFailMessage(() -> {
                        logger.error("Expected url path: {}, but got: {}", expectedURLpath, actualURLpath);
                        return String.format("Expected url path: %s, but got: %s", expectedURLpath, actualURLpath);
                    })
                    .isEqualTo(expectedURLpath);
        }
    }

    @And("Refresh the page")
    public void refresh_the_page() {
        driver.getPage().reload();

        logger.info("Refresh the page has been successfully");
    }
}
