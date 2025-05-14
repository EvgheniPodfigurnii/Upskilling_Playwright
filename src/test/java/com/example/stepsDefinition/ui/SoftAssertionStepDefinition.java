package com.example.stepsDefinition.ui;

import com.example.softAssertion.SoftAssertion;
import io.cucumber.java.en.Then;

public class SoftAssertionStepDefinition {
    @Then("the error message should be {string}")
    public void theErrorMessageShouldBe(String expectedMessage) {
        String actualMessage = "Some actual message"; // This would come from the UI
        SoftAssertion.get().assertEquals(actualMessage, expectedMessage, "Error message mismatch!");
    }
}
