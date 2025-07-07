@UI
Feature: User Login

  Background:
    Given Navigate to "Signup / Login" page

  Scenario: User login with correct credentials in the website
    When The user enters login credentials
    And Click the "Login" button on Signup_Login Page
    Then Click "Logout" header link
    And Check that redirect to "Signup / Login" page

  Scenario: User login in the website and Refresh Page
    When The user enters login credentials
    And Click the "Login" button on Signup_Login Page
    Then Check that Logged in as after "Login"
    And Refresh the page
    Then Check that Logged in as after "Login"

  Scenario Outline: User login with not existing credentials to check boundary values
    When The user login with not existing email "<email>" and password "<password>"
    And Click the "Login" button on Signup_Login Page
    Then The error message "<errorMessage>" should be displayed in the "Login" section

    Examples:
      | email               | password                             | errorMessage                         |
      | a@b.co              | short                                | Your email or password is incorrect! |
      | hfebchscbh@mail.net | hbc465!@#$                           | Your email or password is incorrect! |
      | user@abc.com        | veryveryveryveryveryverylongpassword | Your email or password is incorrect! |

  Scenario Outline: User login with not existing credentials to check popup messages
    When The user login with not existing email "<email>" and password "<password>"
    And Click the "Login" button on Signup_Login Page
    Then Check PopUp "<popupErrorMessage>" message for "<fieldName>" on Login section

    Examples:
      | email   | password | fieldName | popupErrorMessage                                                    |
      | abc     |          | email     | Please include an '@' in the email address. 'abc' is missing an '@'. |
      | abc@    |          | email     | Please enter a part following '@'. 'abc@' is incomplete.             |
      |         | passw0rd | email     | Please fill out this field.                                          |
      | abc@bca |          | password  | Please fill out this field.                                          |
