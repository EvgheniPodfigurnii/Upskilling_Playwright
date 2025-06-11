package com.example.pages.ui;

import com.example.playwrightManager.PlaywrightManager;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPage {
    PlaywrightManager driver = PlaywrightManager.getInstance();
    private static final Logger logger = LogManager.getRootLogger();

    private final Locator emailLogin;
    private final Locator passwordLogin;
    private final Locator nameSignUp;
    private final Locator emailSignUp;
    private final Locator title;
    private final Locator name;
    private final Locator email;
    private final Locator password;
    private final Locator firstName;
    private final Locator lastName;
    private final Locator company;
    private final Locator address1;
    private final Locator address2;
    private final Locator state;
    private final Locator city;
    private final Locator zipcode;
    private final Locator mobileNumber;
    private final Locator days;
    private final Locator months;
    private final Locator years;
    private final Locator country;
    private final Locator newsletter;
    private final Locator specialOffers;
    private final Locator loginFormMessage;
    private final Locator signupFormMessage;
    private final Map<String, Locator> signUpLoginButtons;
    private final Locator emailLoginMessagePopUp = driver.getPage().locator("[data-qa='login-email']");
    private final Locator passwordLoginMessagePopUp = driver.getPage().locator("[data-qa='login-password']");

    public LoginPage() {
        this.emailLogin = driver.getPage().locator("[data-qa='login-email']");
        this.passwordLogin = driver.getPage().locator("[data-qa='login-password']");
        this.nameSignUp = driver.getPage().locator("[data-qa='signup-name']");
        this.emailSignUp = driver.getPage().locator("[data-qa='signup-email']");
        this.title = driver.getPage().locator("input[type='radio'][value='Mr']");
        this.name = driver.getPage().locator("#name");
        this.email = driver.getPage().locator("#email");
        this.password = driver.getPage().locator("#password");
        this.firstName = driver.getPage().locator("#first_name");
        this.lastName = driver.getPage().locator("#last_name");
        this.company = driver.getPage().locator("#company");
        this.address1 = driver.getPage().locator("#address1");
        this.address2 = driver.getPage().locator("#address2");
        this.state = driver.getPage().locator("#state");
        this.city = driver.getPage().locator("#city");
        this.zipcode = driver.getPage().locator("#zipcode");
        this.mobileNumber = driver.getPage().locator("#mobile_number");
        this.days = driver.getPage().locator("#days");
        this.months = driver.getPage().locator("#months");
        this.years = driver.getPage().locator("#years");
        this.country = driver.getPage().locator("#country");
        this.newsletter = driver.getPage().locator("#newsletter");
        this.specialOffers = driver.getPage().locator("#optin");
        this.loginFormMessage = driver.getPage().locator("form[action='/login'] p");
        this.signupFormMessage = driver.getPage().locator("form[action='/signup'] p");
        this.signUpLoginButtons = Map.of(
                "login", driver.getPage().locator("[data-qa='login-button']"),
                "signup", driver.getPage().locator("[data-qa='signup-button']"),
                "create_account", driver.getPage().locator("[data-qa='create-account']"),
                "continue", driver.getPage().locator("[data-qa='continue-button']")
        );
    }

    public Locator getEmailLoginMessagePopUp() {
        return emailLoginMessagePopUp;
    }

    public Locator getPasswordLoginMessagePopUp() {
        return passwordLoginMessagePopUp;
    }

    public void fillEmailLogin(String email) {
        emailLogin.fill(email);
    }

    public void fillPasswordLogin(String password) {
        passwordLogin.fill(password);
    }

    public void fillNameSignUp(String username) {
        nameSignUp.fill(username);
    }

    public void fillEmailSignUp(String email) {
        emailSignUp.fill(email);
    }

    public void clickButtonOnSignUpLoginPage(String buttonName) {
        signUpLoginButtons.get(buttonName).click();
    }

    public void chooseTitle() {
        title.click();
    }

    public String getRegistrationNameWhichCopiedToAccountInformation() {
        return name.getAttribute("value");
    }

    public String getRegistrationEmailWhichCopiedToAccountInformation() {
        return email.getAttribute("value");
    }

    public void fillPassword(String passwordValue) {
        password.fill(passwordValue);
    }

    public void fillDay(String dayValue) {
        days.selectOption(dayValue);
    }

    public void fillMonth(String monthValue) {
        months.selectOption(monthValue);
    }

    public void fillYear(String yearValue) {
        years.selectOption(yearValue);
    }

    public void clickNewsletter() {
        newsletter.click();
    }

    public void clickSpecialOffers() {
        specialOffers.click();
    }

    public void fillFirstName(String firstNameValue) {
        firstName.fill(firstNameValue);
    }

    public void fillLastName(String lastNameValue) {
        lastName.fill(lastNameValue);
    }

    public void fillCompany(String companyValue) {
        company.fill(companyValue);
    }

    public void fillAddress1(String address1Value) {
        address1.fill(address1Value);
    }

    public void fillAddress2(String address2Value) {
        address2.fill(address2Value);
    }

    public void fillCountry(String countryValue) {
        country.selectOption(countryValue);
    }

    public void fillState(String stateValue) {
        state.fill(stateValue);
    }

    public void fillCity(String cityValue) {
        city.fill(cityValue);
    }

    public void fillZipCode(String zipCodeValue) {
        zipcode.fill(zipCodeValue);
    }

    public void fillMobileNumber(String mobileNumberValue) {
        mobileNumber.fill(mobileNumberValue);
    }

    public String getMessageFromLoginSection() {
        return loginFormMessage.textContent();
    }

    public String getMessageFromSignUpSection() {
        return signupFormMessage.textContent();
    }

    public void checkErrorMessage(String sectionName, String expectedMessage, Supplier<String> actualMessageSupplier) {
        String actualMessage = actualMessageSupplier.get();

        if (actualMessage.equals(expectedMessage)) {
            logger.info("PASS: {} message matches: '{}'", sectionName, actualMessage);
        } else {
            assertThat(actualMessage)
                    .withFailMessage(() -> {
                        logger.error("Expected message is: {} -> but got: {}", expectedMessage, actualMessage);
                        return String.format("Expected message is: %s -> but got: %s", expectedMessage, actualMessage);
                    })
                    .isEqualTo(expectedMessage);
        }
    }

    public String getPopUpMessage(Locator locator) {
        ElementHandle handle = locator.elementHandle();
        driver.getPage().waitForFunction(
                "el => el.validationMessage && el.validationMessage.length > 0",
                handle
        );

        return handle.evaluate("el => el.validationMessage").toString();
    }
}
