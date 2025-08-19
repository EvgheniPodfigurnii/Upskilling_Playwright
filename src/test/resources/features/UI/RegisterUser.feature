@UI
Feature: Register User

  Background:
    Given Navigate to "Signup / Login" page

  Scenario: Registering and Deleting the user account on the website
    When The user enters registration credentials
    And Click the "SignUp" button on Signup_Login Page
    And Check if the Registration info has been copied to Account Info
    And The user fills the account information:
      | field           | value  |
      | Newsletter      | yes    |
      | Special_Offers  | yes    |
    And The user fills the address information:
    And Click the "Create Account" button on Signup_Login Page
    And Click the "Continue" button on Signup_Login Page
    Then Check that Logged in as after "SignUp"
    And Check that redirect to "Home" page
    When Click "Delete Account" header link
    And Click the "Continue" button on Signup_Login Page
    Then Check that redirect to "Home" page

  Scenario: Registering the user account, to do Logout and after that try to Login
    When The user enters registration credentials
    And Click the "SignUp" button on Signup_Login Page
    And Check if the Registration info has been copied to Account Info
    And The user fills the account information:
      | field           | value  |
      | Newsletter      | yes    |
      | Special_Offers  | yes    |
    And The user fills the address information:
    And Click the "Create Account" button on Signup_Login Page
    And Click the "Continue" button on Signup_Login Page
    Then Click "Logout" header link
    When The user enters credentials after signup
    And Click the "Login" button on Signup_Login Page
    Then Check that Logged in as after "SignUp"

    Scenario: Register User with existing email
      When The user enters login credentials in the signup section
      And Click the "SignUp" button on Signup_Login Page
      Then The error message "Email Address already exist!" should be displayed in the "Signup" section
