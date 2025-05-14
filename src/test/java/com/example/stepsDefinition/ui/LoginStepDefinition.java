package com.example.stepsDefinition.ui;

import com.example.dataFaker.DataFaker;
import com.example.pages.ui.LoginPage;
import com.example.playwrightManager.PlaywrightManager;
import com.example.softAssertion.SoftAssertion;
import com.example.commonMethods.CommonMethods;
import com.example.utils.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LoginStepDefinition {
    PlaywrightManager driver = PlaywrightManager.getInstance();
    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    LoginPage loginPage = new LoginPage();
    CommonMethods commonMethods = new CommonMethods();
    DataFaker dataFaker = new DataFaker();

//    public LoginStepDefinition(PlaywrightManager driverManager) {
//        this.driver = Objects.requireNonNull(driverManager);
//        this.loginPage = new LoginPage(driverManager);
//    }
//
//    public LoginStepDefinition(ScenarioContext scenarioContext) {
//        this.scenarioContext = scenarioContext;
//    }
//
//    public LoginStepDefinition() {
//        this.commonMethods = new CommonMethods();
//        this.dataFaker = new DataFaker();
//    }

//    LoginPage loginPage = new LoginPage(driver);



    @When("The user enters registration credentials")
    public void user_enters_registration_username_email() {
        String signUpName = dataFaker.createName();
        String signUpEmail = dataFaker.createEmail();

        loginPage.fillNameSignUp(signUpName);
        loginPage.fillEmailSignUp(signUpEmail);

        scenarioContext.set("signUpName", signUpName);
        scenarioContext.set("signUpEmail", signUpEmail);
    }

    @When("The user enters login credentials")
    public void user_enters_login_email_password() {
//        System.out.println(scenarioContext.get("email"));

//        loginPage.fillEmailLogin(scenarioContext.get("email"));
        loginPage.fillEmailLogin(scenarioContext.get("email"));
        loginPage.fillPasswordLogin(scenarioContext.get("password"));
    }

//    @And("Check that 'ENTER ACCOUNT INFORMATION' is visible")
//    public void check_that_account_information_is_visible() {
//        driver.findElement(By.className("login-form")).isDisplayed();
//    }

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

            SoftAssertion.get().assertEquals(actual, expected, "Failed to copy value: " + value);
        }

        SoftAssertion.get().assertAll();
    }

    @Then("The user fills the account information:")
    public void fill_account_info(DataTable table) {
        Map<String, String> data = table.asMap(String.class, String.class);

        loginPage.chooseTitle();
        loginPage.fillPassword(dataFaker.createPassword());
        loginPage.fillDay(dataFaker.createBirthday("day"));
        loginPage.fillMonth(dataFaker.createBirthday("month"));
        loginPage.fillYear(dataFaker.createBirthday("year"));

        if (data.get("Newsletter").equalsIgnoreCase("yes")) {
            loginPage.clickNewsletter();
        }

        if (data.get("Special_Offers").equalsIgnoreCase("yes")) {
            loginPage.clickSpecialOffers();
        }
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
    }

//
//    @Then("Check that ACCOUNT message is visible and contains {string} message")
//    public void check_that_account_message_is_visible(String message) {
//        awaitUtil.checkAccountMessageIsDisplayed(message);
//    }

    @And("Click the {string} button on Signup_Login Page")
    public void click_button_on_signuplogin_page(String nameButton) {
        String label = commonMethods.refactoredUserFriendlyName(nameButton);
        loginPage.clickButtonOnSignUpLoginPage(label.toLowerCase());
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
