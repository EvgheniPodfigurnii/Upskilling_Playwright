package com.example.sections;

import com.example.playwrightManager.PlaywrightManager;

public class HeaderComponent {
    PlaywrightManager driver;

    public HeaderComponent(PlaywrightManager driverManager) {
        this.driver = driverManager;
    }

    String home = "a[href='/']";
    String products = "a[href='/products']";
    String cart = "a[href='/view_cart']";
    String signupLogin = "a[href='/login']";
    String testCases = "a[href='/test_cases']";
    String apiTesting = "a[href='/api_list']";
    String videoTutorials = "a[href='https://www.youtube.com/c/AutomationExercise']";
    String contactUs = "a[href='/contact_us']";
    String logout = "a[href='/logout']";
    String deleteAccount = "a[href='/delete_account']";
    String loggedUser = "//a[contains(text(), 'Logged in as')]/b";

    public void clickHeaderLink(String linkName) {
        switch (linkName.toLowerCase()) {
            case "home":
                driver.findElement(home).getByText("Home").click();
                break;
            case "products":
                driver.findElement(products).getByText("Products").click();
                break;
            case "cart":
                driver.findElement(cart).getByText("Cart").click();
                break;
            case "signup_login":
                driver.findElement(signupLogin).getByText("Signup / Login").click();
                break;
            case "test_cases":
                driver.findElement(testCases).getByText("Test Cases").click();
                break;
            case "api_testing":
                driver.findElement(apiTesting).getByText("API Testing").click();
                break;
            case "video_tutorials":
                driver.findElement(videoTutorials).getByText("Video Tutorials").click();
                break;
            case "contact_us":
                driver.findElement(contactUs).getByText("Contact us").click();
                break;
            case "logout":
                driver.findElement(logout).getByText("Logout").click();
                break;
            case "delete_account":
                driver.findElement(deleteAccount).getByText("Delete Account").click();
                break;
            default:
                throw new IllegalArgumentException("Invalid link name: " + linkName);
        }
    }

    public String getUserLoggedInAs() {
        return driver.findElement(loggedUser).textContent().trim();
    }
}
