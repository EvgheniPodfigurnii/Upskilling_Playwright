
@UI @Run
Feature: User Login

  Background:
    Given Navigate to "Signup / Login" page

  @UI
  Scenario: User login with correct credentials in the website
    When The user enters login credentials
    And Click the "Login" button on Signup_Login Page
    Then Click "Logout" header link
    And Check that redirect to "Signup / Login" page

  @UI
  Scenario: User login in the website and Refresh Page
    When The user enters login credentials
    And Click the "Login" button on Signup_Login Page
    Then Check that Logged in as after "Login"
    And Refresh the page
    Then Check that Logged in as after "Login"

  @UI
  Scenario: User login with not existing credentials
    When The user login with not existing email "hfebchscbh@mail.net" and password "hbc465!@#$"
    And Click the "Login" button on Signup_Login Page
    Then Check error message in the login section "Your email or password is incorrect!"
