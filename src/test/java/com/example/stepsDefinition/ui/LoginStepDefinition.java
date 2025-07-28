package com.example.stepsDefinition.ui;

import com.example.enums.RegistrationForm;
import com.example.pages.ui.LoginPage;
import com.example.helper.CommonMethods;
import com.example.screenshots.ScreenShotConfigurator;
import com.example.utils.ScenarioContext;
import com.microsoft.playwright.Locator;
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

import static org.assertj.core.api.Assertions.assertThat;


public class LoginStepDefinition {
    private static final Logger logger = LogManager.getLogger();
    private final ScenarioContext scenarioContext = ScenarioContext.getInstance();
    private final ScreenShotConfigurator screenShotConfigurator = new ScreenShotConfigurator();
    private final CommonMethods commonMethods = new CommonMethods();
    private final SoftAssert softAssert = new SoftAssert();
    private final LoginPage loginPage = new LoginPage();

    @When("The user enters registration credentials")
    public void userEntersRegistrationUsernameEmail() {
        String signUpName = RegistrationForm.NAME.generate();
        String signUpEmail = RegistrationForm.EMAIL.generate();
        String signUpPassword = RegistrationForm.PASSWORD.generate();

        loginPage.fillNameSignUp(signUpName);
        loginPage.fillEmailSignUp(signUpEmail);

        scenarioContext.set("signUpName", signUpName);
        scenarioContext.set("signUpEmail", signUpEmail);
        scenarioContext.set("signUpPassword", signUpPassword);

        logger.info("Sign Up Name : {}", signUpName);
        logger.info("Sign Up Email : {}", signUpEmail);
    }

    @When("The user enters login credentials")
    public void userEntersLoginEmailPassword() {
        String email = scenarioContext.get("email");
        String password = scenarioContext.get("password");

        loginPage.fillEmailLogin(email);
        loginPage.fillPasswordLogin(password);

        logger.info("Login Email : {}", email);
        logger.info("Login Password : {}", password);
    }

    @When("The user enters login credentials in the signup section")
    public void userLoginWithNotExistingCredentials() {
        String name = scenarioContext.get("username");
        String email = scenarioContext.get("email");

        loginPage.fillNameSignUp(name);
        loginPage.fillEmailSignUp(email);

        logger.info("Fill name: {} - in the signup section", name);
        logger.info("Fill email: {} - in the signup section", email);
    }

    @When("The user enters credentials after signup")
    public void userEntersCredentialsAfterSignup() {
        String email = scenarioContext.get("signUpEmail");
        String password = scenarioContext.get("signUpPassword");

        loginPage.fillEmailLogin(email);
        loginPage.fillPasswordLogin(password);

        logger.info("Login Email after SignUp : {}", email);
        logger.info("Login Password after SignUp : {}", password);
    }

    @When("The user login with not existing email {string} and password {string}")
    public void userLoginWithNotExistingCredentials(String email, String password) {
        loginPage.fillEmailLogin(email);
        loginPage.fillPasswordLogin(password);

        logger.info("Login Email is: {}", email);
        logger.info("Login password is: {}", password);
    }

    @And("Check if the Registration info has been copied to Account Info")
    public void checkRegistrationInfoCopiedToAccountInfo() {
        String userNameActualResult = loginPage.getRegistrationNameWhichCopiedToAccountInformation();
        String userEmailActualResult = loginPage.getRegistrationEmailWhichCopiedToAccountInformation();

        List<String> listOfRegistrationInfo = new ArrayList<>();
        listOfRegistrationInfo.add("name");
        listOfRegistrationInfo.add("email");

        Map<String, String> expectedListOfRegistrationInfo = Map.of(
                "name", scenarioContext.get("signUpName"),
                "email", scenarioContext.get("signUpEmail")
        );

        Map<String, String> actualListOfRegistrationInfo = Map.of(
                "name", userNameActualResult,
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
    public void fillAccountInfo(DataTable table) {
        Map<String, String> data = table.asMap(String.class, String.class);

        loginPage.chooseTitle(RegistrationForm.TITLE.generate());
        loginPage.fillPassword(scenarioContext.get("signUpPassword"));
        loginPage.fillDay(RegistrationForm.BIRTHDAY.generate());
        loginPage.fillMonth(RegistrationForm.BIRTHMONTH.generate());
        loginPage.fillYear(RegistrationForm.BIRTHYEAR.generate());

        if (data.get("Newsletter").equalsIgnoreCase("yes")) {
            loginPage.clickNewsletter();
        }

        if (data.get("Special_Offers").equalsIgnoreCase("yes")) {
            loginPage.clickSpecialOffers();
        }

        logger.info("Account information has been filled to Account Info successfully");
    }

    @And("The user fills the address information:")
    public void fillAddressInfo() {
        loginPage.fillFirstName(RegistrationForm.FIRSTNAME.generate());
        loginPage.fillLastName(RegistrationForm.LASTNAME.generate());
        loginPage.fillCompany(RegistrationForm.COMPANY.generate());
        loginPage.fillAddress1(RegistrationForm.ADDRESS1.generate());
        loginPage.fillAddress2(RegistrationForm.ADDRESS2.generate());
        loginPage.fillCountry(RegistrationForm.COUNTRY.generate());
        loginPage.fillState(RegistrationForm.STATE.generate());
        loginPage.fillCity(RegistrationForm.CITY.generate());
        loginPage.fillZipCode(RegistrationForm.ZIPCODE.generate());
        loginPage.fillMobileNumber(RegistrationForm.MOBILENUMBER.generate());

        logger.info("Address Information has been filled to Address Info successfully");
    }

    @And("Click the {string} button on Signup_Login Page")
    public void clickButtonOnSignupLoginPage(String nameButton) {
        String button = commonMethods.refactoredUserFriendlyName(nameButton);
        loginPage.clickButtonOnSignUpLoginPage(button.toLowerCase());

        logger.info("{} button on Signup_Login Page clicked", button);
    }

    @Then("The error message {string} should be displayed in the {string} section")
    public void checkThatAppearsErrorMessage(String expectedMessage , String sectionName) {
        loginPage.checkErrorMessage(sectionName, expectedMessage, loginPage.getMessageFromLoginSignupSection(sectionName));
    }

    @Then("Check PopUp {string} message for {string} on Login section")
    public void checkPopupMessageOnLoginPage(String expectedMessage, String field) {
        Locator fieldName = null;
        String actualMessage;

        switch (field.toLowerCase()) {
            case "email":
                fieldName = loginPage.getEmailLoginMessagePopUp();
                break;
            case "password":
                fieldName = loginPage.getPasswordLoginMessagePopUp();
                break;
            default:
        }

        assert fieldName != null;
        actualMessage = loginPage.getPopUpMessage(fieldName);


        if (actualMessage.equals(expectedMessage)) {
            logger.info("PASS: {} message matches: '{}'", field, actualMessage);
        } else {
            assertThat(actualMessage)
                    .withFailMessage(() -> {
                        logger.error("Expected message: {}, but got: {}", expectedMessage, actualMessage);
                        return String.format("Expected message: %s, but got: %s", expectedMessage, actualMessage);
                    })
                    .isEqualTo(expectedMessage);
        }
    }
}
