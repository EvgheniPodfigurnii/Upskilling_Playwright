package com.example.stepsDefinition.api;

import com.example.commonMethods.CommonMethods;
import com.example.configurations.ConfigLoader;
import com.example.dataFaker.DataFaker;
import com.example.pages.api.ApiLoginPage;
import com.example.softAssertion.SoftAssertion;
import com.example.utils.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class ApiLoginStepDefinition {
    Logger log = Logger.getLogger(ApiLoginStepDefinition.class.getName());
    CommonMethods commonMethods = new CommonMethods();
    DataFaker dataFaker = new DataFaker();
    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    ApiLoginPage apiLoginPage = new ApiLoginPage();
//
//    public ApiLoginStepDefinition(ScenarioContext scenarioContext) {
//        this.scenarioContext = scenarioContext;
//        this.apiLoginPage = new ApiLoginPage(scenarioContext);
//    }

    private String endpoint;
    private Response response;

    @Given("The API endpoint is {string}")
    public void the_api_endpoint_is(String link) {
        commonMethods.refactoredUserFriendlyName(link);
        endpoint = String.format("%s%s", ConfigLoader.getProperty("base.url"), ConfigLoader.getProperty(String.format("api.%s", link)));

        log.info("API endpoint is: " + endpoint);
    }

//    @And("The API endpoint is {string}")
//    public void api_endpoint_is(String link) {
//        commonMethods.refactoredUserFriendlyName(link);
//        endpoint = String.format("%s%s", ConfigLoader.getProperty("base.url"), ConfigLoader.getProperty(String.format("api.%s", link)));
//        log.info("API endpoint is: " + endpoint);
//    }

    @When("Send a POST request with valid email and password")
    public void send_a_post_request_with_email_and_password() {
        Map<String, String> params = new HashMap<>();
        params.put("email", ConfigLoader.getProperty("email"));
        params.put("password", ConfigLoader.getProperty("password"));

        response = RestAssured.given()
                .formParams(params)
                .when()
                .post(endpoint);

        log.info(response.asString());
    }

    @When("Send a POST request without email parameter")
    public void send_a_post_request_without_email_parameter() {
        Map<String, String> params = new HashMap<>();
        params.put("password", ConfigLoader.getProperty("password"));

        response = RestAssured.given()
                .formParams(params)
                .when()
                .post(endpoint);

        log.info(response.asString());
    }

    @When("Send a POST request with not exist user")
    public void send_a_post_request_without_not_exist_user() {
        Map<String, String> params = new HashMap<>();
        params.put("email", dataFaker.createEmail());
        params.put("password", dataFaker.createPassword());

        response = RestAssured.given()
                .formParams(params)
                .when()
                .post(endpoint);

        log.info(response.asString());
    }

    @When("Create new user account")
    public void create_new_user_account() {
        Map<String, String> params = apiLoginPage.generateUserParams(false);

        response = RestAssured.given()
                .formParams(params)
                .when()
                .post(endpoint);

        log.info(response.asString());
    }

    @When("Create new user account for DELETE flow")
    public void create_new_user_account_delete_flow() {
        Map<String, String> params = apiLoginPage.generateUserParams(false);

        response = RestAssured.given()
                .formParams(params)
                .when()
                .post(endpoint);

        scenarioContext.set("apiEmail", params.get("email"));
        scenarioContext.set("apiPassword", params.get("password"));

        log.info(response.asString());
    }

    @And("DELETE user account")
    public void delete_user_account() {
        Map<String, String> params = new HashMap<>();
        params.put("email", scenarioContext.get("apiEmail"));
        params.put("password", scenarioContext.get("apiPassword"));

        response = RestAssured.given()
                .formParams(params)
                .when()
                .delete(endpoint);

        log.info(response.asString());
    }

    @When("Create new user account for UPDATE flow")
    public void create_new_user_account_update_flow() {
        Map<String, String> params = apiLoginPage.generateUserParams(true);

        response = RestAssured.given()
                .formParams(params)
                .when()
                .post(endpoint);

        log.info(response.asString());
    }

    @When("Get user details")
    public void get_user_details() {
        response = RestAssured.given()
                .queryParam("email", ConfigLoader.getProperty("email"))
                .when()
                .get(endpoint);

        log.info(response.asString());
    }

    @And("Update user account")
    public void update_user_account() {

        Map<String, String> params = new HashMap<>();
        params.put("name", scenarioContext.get("apiname"));
        params.put("email", scenarioContext.get("apiemail"));
        params.put("password", scenarioContext.get("apipassword"));
        params.put("title", scenarioContext.get("apititle"));
        params.put("birth_date", scenarioContext.get("apibirth_date"));
        params.put("birth_month", scenarioContext.get("apibirth_month"));
        params.put("birth_year", scenarioContext.get("apibirth_year"));
        params.put("firstname", scenarioContext.get("apifirstname"));
        params.put("lastname", scenarioContext.get("apilastname"));
        params.put("company", scenarioContext.get("apicompany"));
        params.put("address1", scenarioContext.get("apiaddress1"));
        params.put("address2", scenarioContext.get("apiaddress2"));
        params.put("country", scenarioContext.get("apicountry"));
        params.put("zipcode", scenarioContext.get("apizipcode"));
        params.put("state", scenarioContext.get("apistate"));
        params.put("city", scenarioContext.get("apicity"));
        params.put("mobile_number", scenarioContext.get("apimobile_number"));

        response = RestAssured.given()
                .formParams(params)
                .when()
                .put(endpoint);

        log.info(response.asString());
    }



    @Then("The response code should be {int}")
    public void the_response_status_code_should_be(int expectedResponseCode) {
        SoftAssertion.get().assertEquals(response.getStatusCode(), expectedResponseCode);
        SoftAssertion.get().assertAll();
    }

//    @Then("The response code from JSON should be {int}")
//    public void the_response_code_should_be(int expectedResponseCode) {
//        String responseBody = response.getBody().asString();
//        int actualResponseCode = Integer.parseInt(commonMethods.getValueFromJson(responseBody, "responseCode"));
//
//        assertEquals(actualResponseCode, expectedResponseCode);
//    }
//
//    @And("The response message from JSON should be {string}")
//    public void the_response_message_should_be(String expectedMessage) {
//        String responseBody = response.getBody().asString();
//        String actualResponseMessage = commonMethods.getValueFromJson(responseBody, "message");
//
//        assertEquals(actualResponseMessage, expectedMessage);
//
////        SoftAssertion.get().assertEquals(actualResponseMessage, expectedMessage);
////        SoftAssertion.get().assertAll();
//    }
//
//    @And("JSON should be contains {string} details")
//    public void json_user_details(String object) {
//        String responseBody = response.getBody().asString();
//        String actualResponseMessage = commonMethods.getValueFromJson(responseBody, object);
//
//            if (!actualResponseMessage.isEmpty()) {
//                System.out.println(actualResponseMessage);
//            } else {
//                System.err.println("JSON returned null");
//            }
//    }
}
