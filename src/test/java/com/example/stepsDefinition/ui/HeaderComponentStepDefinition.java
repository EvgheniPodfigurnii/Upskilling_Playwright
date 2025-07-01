package com.example.stepsDefinition.ui;

import com.example.helper.CommonMethods;
import com.example.playwrightManager.PlaywrightManager;
import com.example.configurations.ConfigLoader;
import com.example.enums.PageURL;
import com.example.screenshots.ScreenShotConfigurator;
import com.example.sections.HeaderComponent;
import com.example.utils.ScenarioContext;
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
    HeaderComponent headerComponent = new HeaderComponent(driver);
    Map<String, String> actualUrls = new HashMap<>();
    CommonMethods commonMethods = new CommonMethods();
    SoftAssert softAssert = new SoftAssert();
    ScreenShotConfigurator screenShotConfigurator = new ScreenShotConfigurator();

    @When("Click each header link and check the result")
    public void click_each_header_link_and_collect_result() {
        String expected;

        for (String link : PageURL.keyLabelHeaderLinks.values().stream().sorted().toList()) {
            headerComponent.clickHeaderLink(link);
            actualUrls.put(link, driver.getPage().url());

            String actual = actualUrls.get(link);
            if (link.equalsIgnoreCase(PageURL.VIDEO_TUTORIALS.name())) {
                expected = PageURL.VIDEO_TUTORIALS.getPath();
            } else {
                expected = String.format("%s%s", ConfigLoader.getProperty("base.url"), PageURL.getLabelPageUrlList(link.toUpperCase()));
            }

            if (!actual.equalsIgnoreCase(expected)) {
                screenShotConfigurator.takeScreenshot();
                softAssert.assertEquals(actual, expected,  "Failed at link: " + link);
                logger.error("Expected result is: {} -> Actual result is: {}", expected, actual);
            }
        }

        softAssert.assertAll();
        logger.info("Clicked all header links");
    }

    @When("Click {string} header link")
    public void click_header_link(String linkName) {
        String headerLink = commonMethods.refactoredUserFriendlyName(linkName);
        headerComponent.clickHeaderLink(headerLink);

        logger.info("Clicked header link: {}", headerLink);
    }

    @Then("Check that Logged in as after {string}")
    public void check_that_logged_in_as_after(String action) {
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
