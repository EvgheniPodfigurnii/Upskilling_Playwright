package com.example.stepsDefinition.ui;

import com.example.softAssertion.SoftAssertion;
import com.example.playwrightManager.PlaywrightManager;
import com.example.configurations.ConfigLoader;
import com.example.commonMethods.CommonMethods;
import com.example.enums.PageURL;
import com.example.sections.HeaderComponent;
import com.example.utils.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.*;

public class HeaderComponentStepDefinition {
//    PlaywrightManager driver;
    HeaderComponent headerComponent = new HeaderComponent(PlaywrightManager.getInstance());
//    CommonMethods commonMethods;
    ScenarioContext scenarioContext = ScenarioContext.getInstance();

    PlaywrightManager driver = PlaywrightManager.getInstance(); // singleton
    CommonMethods commonMethods = new CommonMethods();

    Map<String, String> actualUrls = new HashMap<>();

//    public HeaderComponentStepDefinition(PlaywrightManager driverManager, ScenarioContext scenarioContext) {
////        this.driver = Objects.requireNonNull(driverManager);
////        this.headerComponent = new HeaderComponent(driver);
////        this.commonMethods = new CommonMethods();
////        this.scenarioContext = scenarioContext;
//    }


    @When("Click each header link and collect the result")
    public void click_each_header_link_and_collect_result() {
        for (String link : PageURL.keyLabelHeaderLinks.values().stream().sorted().toList()) {
            headerComponent.clickHeaderLink(link);
            actualUrls.put(link, driver.getPage().url());
        }
    }

    @When("Click {string} header link")
    public void click_header_link(String linkName) {
        String headerLink = commonMethods.refactoredUserFriendlyName(linkName);
        headerComponent.clickHeaderLink(headerLink);
    }

    @Then("All header links should navigate to correct URLs")
    public void all_links_should_be_correct() {
        String expected;

        for (String link : PageURL.keyLabelHeaderLinks.values()) {
            String actual = actualUrls.get(link);

            if (link.equalsIgnoreCase(PageURL.VIDEO_TUTORIALS.name())) {
                expected = PageURL.VIDEO_TUTORIALS.getPath();
            } else {
                expected = String.format("%s%s", ConfigLoader.getProperty("base.url"), PageURL.getLabelPageUrlList(link.toUpperCase()));
            }

            SoftAssertion.get().assertEquals(actual, expected, "Failed at link: " + link);
        }

        SoftAssertion.get().assertAll();
    }

//    @Then("Check that 'Logged in as after {string}'")
//    public void check_that_logged_in_as_username_is_visible(String username) {
//        String expectedUserName = "";
//        String actualUsername = headerComponent.getUserLoggedInAs();
//
//        switch (username.toLowerCase()) {
//            case "login":
//                expectedUserName = scenarioContext.get("username");
//                break;
//            case "signup":
//                expectedUserName = scenarioContext.get("signUpName");
//                break;
//            default:
//        }
//
//        SoftAssertion.get().assertEquals(expectedUserName, actualUsername);
//        SoftAssertion.get().assertAll();
//    }
}
