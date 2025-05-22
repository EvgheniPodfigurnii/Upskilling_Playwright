package com.example.pages.ui;

import com.example.playwrightManager.PlaywrightManager;
import com.microsoft.playwright.Locator;
import java.util.Map;

public class LoginPage {
    PlaywrightManager driver = PlaywrightManager.getInstance();

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
    private final Locator message;
    private final Map<String, Locator> signUpLoginButtons;

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
        this.message = driver.getPage().locator("//p[contains(text(), '')]");
        this.signUpLoginButtons = Map.of(
                "login", driver.getPage().locator("[data-qa='login-button']"),
                "signup", driver.getPage().locator("[data-qa='signup-button']"),
                "create_account", driver.getPage().locator("[data-qa='create-account']"),
                "continue", driver.getPage().locator("[data-qa='continue-button']")
        );
    }

    public String getMessage() {
        return message.getAttribute("value");
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
}
