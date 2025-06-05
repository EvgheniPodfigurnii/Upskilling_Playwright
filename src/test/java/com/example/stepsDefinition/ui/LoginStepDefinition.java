package com.example.stepsDefinition.ui;

import com.example.dataFaker.DataFaker;
import com.example.pages.ui.LoginPage;
import com.example.commonMethods.CommonMethods;
import com.example.screenshots.ScreenShotConfigurator;
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

import static org.junit.Assert.assertEquals;


public class LoginStepDefinition {
    private static final Logger logger = LogManager.getRootLogger();
    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    ScreenShotConfigurator screenShotConfigurator = new ScreenShotConfigurator();
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
        String email = scenarioContext.get("email");
        String password = scenarioContext.get("password");

        loginPage.fillEmailLogin(email);
        loginPage.fillPasswordLogin(password);

        logger.info("Login Email : {}", email);
        logger.info("Login Password : {}", password);
    }

    @When("The user enters login credentials in the signup section")
    public void user_login_with_not_existing_credentials() {
        String name = scenarioContext.get("username");
        String email = scenarioContext.get("email");

        loginPage.fillNameSignUp(name);
        loginPage.fillEmailSignUp(email);

        logger.info("Fill name: {} - in the signup section", name);
        logger.info("Fill email: {} - in the signup section", email);
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

    @When("The user login with not existing email {string} and password {string}")
    public void user_login_with_not_existing_credentials(String email, String password) {
        loginPage.fillEmailLogin(email);
        loginPage.fillPasswordLogin(password);

        logger.info("Login Email is: {}", email);
        logger.info("Login password is: {}", password);
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

            if (!actual.equals(expected)) {
                screenShotConfigurator.takeScreenshot();
                softAssert.assertEquals(actual, expected, "Failed to copy value: " + value);
                logger.error("Expected result is: {} -> Actual result is: {}", expected, actual);
            }
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

    @And("Click the {string} button on Signup_Login Page")
    public void click_button_on_signuplogin_page(String nameButton) {
        String button = commonMethods.refactoredUserFriendlyName(nameButton);
        loginPage.clickButtonOnSignUpLoginPage(button.toLowerCase());

        logger.info("{} button on Signup_Login Page clicked", button);
    }

    @Then("Check error message in the login section {string}")
    public void check_error_message_login_section(String expectedMessage) {
        loginPage.checkErrorMessage("Login", expectedMessage, () -> loginPage.getMessageFromLoginSection());
    }

    @Then("Check error message in the signup section {string}")
    public void check_error_message_signup_section(String expectedMessage) {
        loginPage.checkErrorMessage("SignUp", expectedMessage, () -> loginPage.getMessageFromSignUpSection());
    }
}
