
@UI @Run @sss @8040
Feature: User Login

  @UI @Run @sss
  Scenario: User login with correct credentials in the website
    Given Navigate to "Signup / Login" page
    When The user enters login credentials
    And Click the "Login" button on Signup_Login Page
    Then Check that Logged in as after "Login"
    Then Click "Logout" header link
    And Check that redirect to "Signup / Login" page

  Scenario: User login in the website and Refresh Page
    Given Navigate to "Signup / Login" page
    When The user enters login credentials
    And Click the "Login" button on Signup_Login Page
    Then Check that Logged in as after "Login"
    And Refresh the page
    Then Check that Logged in as after "Login"

    @8071
  Scenario: User login with not existing credentials
    Given Navigate to "Signup / Login" page
    When The user login with not existing email "hfebchscbh@mail.net" and password "hbc465!@#$"
    And Click the "Login" button on Signup_Login Page
    Then Check error message in the login section "Your email or pasword is incorrect!"
