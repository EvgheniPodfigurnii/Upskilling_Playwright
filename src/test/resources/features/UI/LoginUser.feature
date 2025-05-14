
@LoginSteps
Feature: User Login

  Background:
    Given Navigate to "Signup / Login" page

  Scenario: User logs with correct credentials in the website
    When The user enters login credentials
    And Click the "Login" button on Signup_Login Page
#    Then Check that 'Logged in as after "Login"'


  Scenario: User logs with correct credentials in the website and logout
    When The user enters login credentials
    And Click the "Login" button on Signup_Login Page
    Then Click "Logout" header link
