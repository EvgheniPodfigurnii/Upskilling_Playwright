package com.example.sections;

import com.example.playwrightManager.PlaywrightManager;

public class HeaderComponent {
    PlaywrightManager driver = PlaywrightManager.getInstance();

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


//    public HeaderComponent(PlaywrightManager driver) {
//        this.driver = driver;
//    }

    public void clickHeaderLink(String linkName) {
        switch (linkName.toLowerCase()) {
            case "home":
                driver.findElement(home).getByText("Home").nth(0).click();
                break;
            case "products":
                driver.findElement(products).getByText("Products").nth(0).click();
                break;
            case "cart":
                driver.findElement(cart).getByText("Cart").nth(0).click();
                break;
            case "signup_login":
                driver.findElement(signupLogin).getByText("Signup / Login").nth(0).click();
                break;
            case "test_cases":
                driver.findElement(testCases).getByText("Test Cases").nth(0).click();
                break;
            case "api_testing":
                driver.findElement(apiTesting).getByText("API Testing").nth(0).click();
                break;
            case "video_tutorials":
                driver.findElement(videoTutorials).getByText("Video Tutorials").nth(0).click();
                break;
            case "contact_us":
                driver.findElement(contactUs).getByText("Contact us").nth(0).click();
                break;
            case "logout":
                driver.findElement(logout).getByText("Logout").nth(0).click();
                break;
            case "delete_account":
                driver.findElement(deleteAccount).getByText("Delete Account").nth(0).click();
                break;
            default:
                throw new IllegalArgumentException("Invalid link name: " + linkName);
        }
    }

    public String getUserLoggedInAs() {
        return driver.findElement(loggedUser).textContent().trim();
    }
}
