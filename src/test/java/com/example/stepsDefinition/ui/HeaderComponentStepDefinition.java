package com.example.stepsDefinition.ui;

import com.example.configurations.ConfigLoader;
import com.example.enums.PageURL;
import com.example.helper.CommonMethods;
import com.example.playwrightManager.PlaywrightManager;
import com.example.sections.HeaderComponent;
import com.example.utils.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HeaderComponentStepDefinition {
    private static final Logger logger = LogManager.getRootLogger();
    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    PlaywrightManager driver = PlaywrightManager.getInstance();
    HeaderComponent headerComponent = new HeaderComponent();
    CommonMethods commonMethods = new CommonMethods();
    SoftAssert softAssert = new SoftAssert();

    @When("Click each header link")
    public void clickEachHeaderLink(DataTable dataTable) {
        List<String> headerLinks = dataTable.asList();

        for (String link : headerLinks) {
            headerComponent.clickHeaderLink(link);
            scenarioContext.set(link, driver.getPage().url());
        }
    }

    @Then("Check the results after click header links")
    public void checkTheResults() {
        String expected;
        Collection<String> headerLinks = PageURL.keyLabelHeaderLinks.values();

        for (String link : headerLinks) {
            String actual = scenarioContext.get(link.toUpperCase());

            if (link.equalsIgnoreCase(PageURL.VIDEO_TUTORIALS.name())) {
                expected = PageURL.VIDEO_TUTORIALS.getPath();
            } else {
                expected = String.format("%s%s", ConfigLoader.getProperty("base.url"), PageURL.getLabelPageUrlList(link.toUpperCase()));
            }

            if (!actual.equalsIgnoreCase(expected)) {
                softAssert.assertEquals(actual, expected,  "Failed at link: " + link);
                logger.error("Expected result is: {} -> Actual result is: {}", expected, actual);
            } else {
                logger.info("All header references follow the correct path");
            }
        }

        softAssert.assertAll();
    }

    @When("Click {string} header link")
    public void clickHeaderLink(String linkName) {
        String headerLink = commonMethods.refactoredUserFriendlyName(linkName);
        headerComponent.clickHeaderLink(headerLink);

        logger.info("Clicked header link: {}", headerLink);
    }

    @Then("Check that Logged in as after {string}")
    public void checkThatLoggedInAsAfter(String action) {
        String expectedUserName = "";
        String actualUsername = headerComponent.getUserLoggedInAs();

        switch (action.toLowerCase()) {
            case "login":
                expectedUserName = scenarioContext.get("username");
                break;
            case "signup":
                expectedUserName = scenarioContext.get("signUpName");
                break;
            default:
                throw new IllegalArgumentException("Unsupported action: " + action);
        }

        if (actualUsername.equals(expectedUserName)) {
            logger.info("Logged in as: {} - after {}", actualUsername, action);
        } else {
        String finalExpectedUserName = expectedUserName;
        assertThat(actualUsername)
                    .withFailMessage(() -> {
                        logger.error("Expected Logged after {} is: {}, but got: {}", action, finalExpectedUserName, actualUsername);
                        return String.format("Expected Logged after %s is: %s, but got: %s", action, finalExpectedUserName, actualUsername);
                    })
                    .isEqualTo(expectedUserName);
        }
    }
}
