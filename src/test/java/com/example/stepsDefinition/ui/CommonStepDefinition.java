package com.example.stepsDefinition.ui;

import com.example.playwrightManager.PlaywrightManager;
import com.example.configurations.ConfigLoader;
import com.example.helper.CommonMethods;
import com.example.enums.PageURL;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.Response;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;


public class CommonStepDefinition {
    private static final Logger logger = LogManager.getLogger();
    private final PlaywrightManager driver = PlaywrightManager.getInstance();
    private final CommonMethods commonMethods = new CommonMethods();

    @Given("Navigate to {string} page")
    public void navigateToPage(String page) {
        String label = commonMethods.refactoredUserFriendlyName(page);

        Allure.step(String.format("Navigate to %s page", page), () -> {
            try {
                driver.navigateTo(String.format("%s%s", ConfigLoader.getProperty("base.url"), PageURL.valueOf(label.toUpperCase()).getPath()));
                logger.info("Navigate to {} page", page);
            } catch (PlaywrightException exception) {
                logger.error("Failed navigate to {} page, and got Error: {}", page, exception.getMessage());
            }
        });
    }

    @When("Refresh the page")
    public void refreshThePage() {
        Response response = driver.getPage().reload();

        Allure.step(String.format("After refresh the page has been status code: %s", response.status()), () -> {
            try {
                logger.info("After refresh the page has been successfully. Status: {}", response.status());
            } catch (PlaywrightException exception) {
                logger.error("After refresh page got an error: {}", exception.getMessage());
            }
        });
    }

    @Then("Check that redirect to {string} page")
    public void redirectToPage(String page) {
        String label = commonMethods.refactoredUserFriendlyName(page);
        String expectedURLpath = String.format("%s%s", ConfigLoader.getProperty("base.url"), PageURL.valueOf(label.toUpperCase()).getPath());
        String actualURLpath = driver.getPage().url();

        if (actualURLpath.equals(expectedURLpath)) {
            logger.info("Redirect to {} page", page);
            Allure.step(String.format("Redirect to %s page", page));
        } else {
            assertThat(actualURLpath)
                    .withFailMessage(() -> {
                        logger.error("Expected url path: {}, but got: {}", expectedURLpath, actualURLpath);
                        Allure.step(String.format("Expected url path: %s, but got: %s", expectedURLpath, actualURLpath));
                        return String.format("Expected url path: %s, but got: %s", expectedURLpath, actualURLpath);
                    })
                    .isEqualTo(expectedURLpath);
        }
    }
}
