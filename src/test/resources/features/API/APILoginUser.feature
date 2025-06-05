
@API @Run
Feature: Verify Login API

  @API
  Scenario: Verify login with valid email and password
    Given The API endpoint is "login"
    When Send a POST request with valid email and password
    Then The response code from JSON should be 200
    And The response message from JSON should be "User exists!"

  @API
  Scenario: Verify login without email parameter
    Given The API endpoint is "login"
    When Send a POST request without email parameter
    Then The response code from JSON should be 400
    And The response message from JSON should be "Bad request, email or password parameter is missing in POST request."

  @API
  Scenario: Verify login with not existing user
    Given The API endpoint is "login"
    When Send a POST request with not exist user
    Then The response code from JSON should be 404
    And The response message from JSON should be "User not found!"

  @API
  Scenario: Register new user account
    Given The API endpoint is "create"
    When Create new user account
    Then The response code from JSON should be 201
    And The response message from JSON should be "User created!"

  @API
  Scenario: Delete user account
    Given The API endpoint is "create"
    When Create new user account for DELETE flow
    And The API endpoint is "delete"
    And DELETE user account
    Then The response code from JSON should be 200
    And The response message from JSON should be "Account deleted!"

  @API
  Scenario: Update user account
    Given The API endpoint is "create"
    When Create new user account for UPDATE flow
    And The API endpoint is "update"
    And Update user account
    Then The response code from JSON should be 200
    And The response message from JSON should be "User updated!"

  @API
  Scenario: Get user details by email
    Given The API endpoint is "get user details"
    When Get user details
    Then The response code from JSON should be 200
    And JSON should be contains "user" details
