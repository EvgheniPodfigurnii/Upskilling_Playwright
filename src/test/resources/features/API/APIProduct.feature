
@API @Run
Feature: Verify Product API

  Background:
    Given The API endpoint is "products list"

  @API
  Scenario: GET request to All Products List
    When Send "GET" request
    Then The response code from JSON should be 200
    And JSON should be contains "products" details

  @API
  Scenario: POST request to All Products List
    When Send "POST" request
    Then The response code from JSON should be 405
    And The response message from JSON should be "This request method is not supported."