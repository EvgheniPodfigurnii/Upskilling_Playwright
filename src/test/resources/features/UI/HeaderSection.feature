@UI
Feature: MainPage

  Scenario: Check header links
    Given Navigate to "Home" page
    When Click each header link
    | HOME            |
    | PRODUCTS        |
    | CART            |
    | SIGNUP_LOGIN    |
    | TEST_CASES      |
    | API_TESTING     |
    | CONTACT_US      |
    | VIDEO_TUTORIALS |
    Then Check the results after click header links
