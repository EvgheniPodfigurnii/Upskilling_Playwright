
@API
Feature: Verify Login API

  Scenario Outline: Verify login with valid email and password
    Given The API endpoint is "<endpoint>"
    When Send a POST request with "<email>" and "<password>"
    Then The response code from JSON should be <responseCode>
    And The response message from JSON should be "<responseMessage>"

    Examples:
    | endpoint | email                  | password     | responseCode | responseMessage                                                      |
    | LOGIN    | newemail8@newemail.com | newspass123! | 200          | User exists!                                                         |
    | LOGIN    |                        | newspass123! | 400          | Bad request, email or password parameter is missing in POST request. |
    | LOGIN    | notexistuser@email.com | newpass12!!! | 404          | User not found!                                                      |

  Scenario: Register new user account
    Given The API endpoint is "CREATE"
    When Create new user account
    Then The response code from JSON should be 201
    And The response message from JSON should be "User created!"

  Scenario: Delete user account
    Given The API endpoint is "CREATE"
    When Create new user account for DELETE flow
    And The API endpoint is "DELETE"
    And DELETE user account
    Then The response code from JSON should be 200
    And The response message from JSON should be "Account deleted!"

  Scenario: Update user account
    Given The API endpoint is "CREATE"
    When Create new user account for UPDATE flow
    And The API endpoint is "UPDATE"
    And Update user account
    Then The response code from JSON should be 200
    And The response message from JSON should be "User updated!"

  Scenario: Get user details by email
    Given The API endpoint is "GET_USER_DETAILS"
    When Get user details
    Then The response code from JSON should be 200
    And JSON should be contains "user" details
