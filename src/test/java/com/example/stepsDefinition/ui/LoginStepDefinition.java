package com.example.stepsDefinition.ui;

import com.example.dataFaker.DataFaker;
import com.example.pages.ui.LoginPage;
import com.example.commonMethods.CommonMethods;
import com.example.utils.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LoginStepDefinition {
    private static final Logger logger = LogManager.getRootLogger();
    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    CommonMethods commonMethods = new CommonMethods();
    SoftAssert softAssert = new SoftAssert();
    LoginPage loginPage = new LoginPage();
    DataFaker dataFaker = new DataFaker();

    @When("The user enters registration credentials")
    public void user_enters_registration_username_email() {
        String signUpName = dataFaker.createName();
        String signUpEmail = dataFaker.createEmail();
        String signUpPassword = dataFaker.createPassword();

        loginPage.fillNameSignUp(signUpName);
        loginPage.fillEmailSignUp(signUpEmail);

        scenarioContext.set("signUpName", signUpName);
        scenarioContext.set("signUpEmail", signUpEmail);
        scenarioContext.set("signUpPassword", signUpPassword);

        logger.info("Sign Up Name : {}", signUpName);
        logger.info("Sign Up Email : {}", signUpEmail);
    }

    @When("The user enters login credentials")
    public void user_enters_login_email_password() {
        loginPage.fillEmailLogin(scenarioContext.get("email"));
        loginPage.fillPasswordLogin(scenarioContext.get("password"));

        logger.info("Login Email : {} \n Login Password : {}", scenarioContext.get("email"), scenarioContext.get("password"));
    }

    @When("The user enters credentials after signup")
    public void user_enters_credentials_after_signup() {
        String email = scenarioContext.get("signUpEmail");
        String password = scenarioContext.get("signUpPassword");

        loginPage.fillEmailLogin(email);
        loginPage.fillPasswordLogin(password);

        logger.info("Login Email after SignUp : {}", email);
        logger.info("Login Password after SignUp : {}", password);
    }

    @And("Check if the Registration info has been copied to Account Info")
    public void check_registration_info_copied_to_Account_info() {
        String userNameActualResult = loginPage.getRegistrationNameWhichCopiedToAccountInformation();
        String userEmailActualResult = loginPage.getRegistrationEmailWhichCopiedToAccountInformation();

        List<String> listOfRegistrationInfo = new ArrayList<>();
        listOfRegistrationInfo.add("name");
        listOfRegistrationInfo.add("email");

        Map<String, String> expectedListOfRegistrationInfo = Map.of(
                "name ", scenarioContext.get("signUpName"),
                "email", scenarioContext.get("signUpEmail")
        );

        Map<String, String> actualListOfRegistrationInfo = Map.of(
                "name ", userNameActualResult,
                "email", userEmailActualResult
        );

        for (String value : listOfRegistrationInfo) {
            String actual = actualListOfRegistrationInfo.get(value);
            String expected = expectedListOfRegistrationInfo.get(value);

            softAssert.assertEquals(actual, expected, "Failed to copy value: " + value);
        }

        softAssert.assertAll();

        logger.info("Registration name and email has been copied to Account Info successfully");
    }

    @Then("The user fills the account information:")
    public void fill_account_info(DataTable table) {
        Map<String, String> data = table.asMap(String.class, String.class);

        loginPage.chooseTitle();
        loginPage.fillPassword(scenarioContext.get("signUpPassword"));
        loginPage.fillDay(dataFaker.createBirthday("day"));
        loginPage.fillMonth(dataFaker.createBirthday("month"));
        loginPage.fillYear(dataFaker.createBirthday("year"));

        if (data.get("Newsletter").equalsIgnoreCase("yes")) {
            loginPage.clickNewsletter();
        }

        if (data.get("Special_Offers").equalsIgnoreCase("yes")) {
            loginPage.clickSpecialOffers();
        }

        logger.info("Account information has been filled to Account Info successfully");
    }

    @And("The user fills the address information:")
    public void fill_address_info(DataTable table) {
        Map<String, String> data = table.asMap(String.class, String.class);

        loginPage.fillFirstName(dataFaker.createFirstName());
        loginPage.fillLastName(dataFaker.createLastName());
        loginPage.fillCompany(dataFaker.createCompany());
        loginPage.fillAddress1(dataFaker.createAddress());
        loginPage.fillAddress2(dataFaker.createAddress2());
        loginPage.fillCountry(data.get("Country"));
        loginPage.fillState(dataFaker.createState());
        loginPage.fillCity(dataFaker.createCity());
        loginPage.fillZipCode(dataFaker.createZipCode());
        loginPage.fillMobileNumber(dataFaker.createMobilePhone());

        logger.info("Address Information has been filled to Address Info successfully");
    }

//
//    @Then("Check that ACCOUNT message is visible and contains {string} message")
//    public void check_that_account_message_is_visible(String message) {
//        awaitUtil.checkAccountMessageIsDisplayed(message);
//    }

    @And("Click the {string} button on Signup_Login Page")
    public void click_button_on_signuplogin_page(String nameButton) {
        String button = commonMethods.refactoredUserFriendlyName(nameButton);
        loginPage.clickButtonOnSignUpLoginPage(button.toLowerCase());

        logger.info("{} button on Signup_Login Page clicked", button);
    }

//    @And("The URL of the {string} page must be correct")
//    public void verify_login_url(String page) {
//        String expectedUrl = String.format("%s%s", ConfigLoader.getProperty("base.url"), page.toLowerCase());
//        String actualUrl = loginPage.getCurrentURL();
//
//        SoftAssertion.get().assertEquals(expectedUrl, actualUrl, "The current URL is not as expected");
//        SoftAssertion.get().assertAll();
//    }
}
