
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
      | a@b.co       | short                                | Your email or password is incorrect! |
      | user@abc.com | veryveryveryveryveryverylongpassword | Your email or password is incorrect! |

  @UI
  Scenario: User login with not existing credentials to check popup messages without '@'
    When The user login with not existing email "abc" and password ""
    And Click the "Login" button on Signup_Login Page
    Then Check PopUp "Please include an '@' in the email address. 'abc' is missing an '@'." message for "email" on Login section

  @UI
  Scenario: User login with not existing credentials to check popup messages with only "abc@"
    When The user login with not existing email "abc@" and password ""
    And Click the "Login" button on Signup_Login Page
    Then Check PopUp "Please enter a part following '@'. 'abc@' is incomplete." message for "email" on Login section

  @UI
  Scenario: User login with not existing credentials to check popup messages without email
    When The user login with not existing email "" and password "passw0rd"
    And Click the "Login" button on Signup_Login Page
    Then Check PopUp "Please fill out this field." message for "email" on Login section

  @UI
  Scenario: User login with not existing credentials to check popup messages without password
    When The user login with not existing email "abc@bca" and password ""
    And Click the "Login" button on Signup_Login Page
    Then Check PopUp "Please fill out this field." message for "password" on Login section
