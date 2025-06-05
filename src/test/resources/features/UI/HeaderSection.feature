
@UI @Run
Feature: MainPage

  @UI
  Scenario: Check header links
    Given Navigate to "Home" page
    When Click each header link and collect the result
