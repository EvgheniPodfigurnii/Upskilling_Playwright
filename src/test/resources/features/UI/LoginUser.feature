
@UI @Run @sss @8040
Feature: User Login

  @UI @Run @sss
  Scenario: User logs with correct credentials in the website
    Given Navigate to "Signup / Login" page
    When The user enters login credentials
    And Click the "Login" button on Signup_Login Page
    Then Check that Logged in as after "Login"
    Then Click "Logout" header link
    And Check that redirect to "Signup / Login" page

  Scenario: User logs with correct credentials in the website
    Given Navigate to "Signup / Login" page
    When The user enters login credentials
    And Click the "Login" button on Signup_Login Page
    Then Check that Logged in as after "Login"
    And Refresh the page
    Then Check that Logged in as after "Login"
