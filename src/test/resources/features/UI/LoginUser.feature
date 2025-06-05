
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


  @UI
  Scenario Outline: User login with not existing credentials to check boundary values
    When The user login with not existing email "<email>" and password "<password>"
    And Click the "Login" button on Signup_Login Page
    Then Check error message in the login section "<errorMessage>"

  Examples:
    Examples:
      | email        | password                             | errorMessage                         |
      | test@abc.com |                                      | Password is required                 |
      |              | P@ssword123                          | Email address is required            |
      | a@b.co       | short                                | Your email or password is incorrect! |
      | user@abc.com | veryveryveryveryveryverylongpassword | Your email or password is incorrect! |