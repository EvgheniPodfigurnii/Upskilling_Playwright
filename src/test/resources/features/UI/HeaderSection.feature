
@Run @sss
Feature: MainPage

  @Run @sss @803
  Scenario: Check header links
    Given Navigate to "Home" page
    When Click each header link and collect the result
    Then All header links should navigate to correct URLs
