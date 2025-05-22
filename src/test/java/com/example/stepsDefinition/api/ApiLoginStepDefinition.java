package com.example.stepsDefinition.api;

import com.example.commonMethods.CommonMethods;
import com.example.configurations.ConfigLoader;
import com.example.dataFaker.DataFaker;
import com.example.pages.api.ApiLoginPage;
import com.example.utils.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

public class ApiLoginStepDefinition {
    private static final Logger logger = LogManager.getLogger("scenario");
    CommonMethods commonMethods = new CommonMethods();
    DataFaker dataFaker = new DataFaker();
    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    ApiLoginPage apiLoginPage = new ApiLoginPage();
    SoftAssert softAssert = new SoftAssert();
    private String endpoint;
    private Response response;

    @Given("The API endpoint is {string}")
    public void the_api_endpoint_is(String link) {
        String linkAfterRefactoring = commonMethods.refactoredUserFriendlyName(link);
        endpoint = String.format("%s%s", ConfigLoader.getProperty("base.url"), ConfigLoader.getProperty(String.format("api.%s", linkAfterRefactoring)));

        logger.info("API endpoint is: {}", endpoint);
    }

    @When("Send a POST request with valid email and password")
    public void send_a_post_request_with_email_and_password() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Map<String, String> params = new HashMap<>();
        params.put("email", ConfigLoader.getProperty("email"));
        params.put("password", ConfigLoader.getProperty("password"));


        response = RestAssured.given()
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .formParams(params)
                .when()
                .post(endpoint);

        logger.info("Response after POST request with valid email and password is : {}", response.getStatusCode());
    }

    @When("Send a POST request without email parameter")
    public void send_a_post_request_without_email_parameter() {
        Map<String, String> params = new HashMap<>();
        params.put("password", ConfigLoader.getProperty("password"));

        response = RestAssured.given()
                .formParams(params)
                .when()
                .post(endpoint);

        logger.info("Response after POST request without email parameter is : {}", response.getStatusCode());
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

        logger.info("Response after POST request with not exist user is : {}", response.getStatusCode());
    }

    @When("Create new user account")
    public void create_new_user_account() {
        Map<String, String> params = apiLoginPage.generateUserParams(false);

        response = RestAssured.given()
                .formParams(params)
                .when()
                .post(endpoint);

        logger.info("Response after Create new user account is : {}", response.getStatusCode());
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

        logger.info("Response after Create new user account for DELETE flow is : {}", response.getStatusCode());
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

        logger.info("Response after DELETE user account is : {}", response.getStatusCode());
    }

    @When("Create new user account for UPDATE flow")
    public void create_new_user_account_update_flow() {
        Map<String, String> params = apiLoginPage.generateUserParams(true);

        response = RestAssured.given()
                .formParams(params)
                .when()
                .post(endpoint);

        logger.info("Response after Create new user account for UPDATE flow is : {}", response.getStatusCode());
    }

    @When("Get user details")
    public void get_user_details() {
        response = RestAssured.given()
                .queryParam("email", ConfigLoader.getProperty("email"))
                .when()
                .get(endpoint);

        logger.info("Response after Get user details is : {}", response.getStatusCode());
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

        logger.info("Response after Update user account is : {}", response.getStatusCode());
    }

    @Then("The response code from JSON should be {int}")
    public void the_response_code_should_be(int expectedResponseCode) {
        String responseBody = response.getBody().asString();

        int actualResponseCode = Integer.parseInt(commonMethods.getValueFromJson(responseBody, "responseCode"));

        softAssert.assertEquals(actualResponseCode, expectedResponseCode);
        softAssert.assertAll();

        logger.info("The response code from JSON : {}", actualResponseCode);
    }

    @And("The response message from JSON should be {string}")
    public void the_response_message_should_be(String expectedMessage) {
            String responseBody = response.getBody().asString();
            String actualResponseMessage = commonMethods.getValueFromJson(responseBody, "message");

            softAssert.assertEquals(expectedMessage, actualResponseMessage);
            softAssert.assertAll();

            logger.info("Response message from JSON : {}", actualResponseMessage);
    }

    @And("JSON should be contains {string} details")
    public void json_user_details(String object) {
        String responseBody = response.getBody().asString();
        String actualResponseMessage = commonMethods.getValueFromJson(responseBody, object);

        if (!actualResponseMessage.isEmpty()) {
            logger.info("Response message from JSON contains {} details : {}", object, actualResponseMessage);
        } else {
            logger.error("JSON returned null");
        }
    }

    @When("Send {string} request")
    public void send_request(String method) {
        switch (method.toUpperCase()) {
            case "GET":
                response = RestAssured.given()
                        .when()
                        .get(endpoint);
                break;
            case "POST":
                response = RestAssured.given()
                        .when()
                        .post(endpoint);
                break;
            default:
                throw new IllegalArgumentException("Unsupported method: " + method);
        }

        logger.info("{} request was successfully sent. Status: {}", method.toUpperCase(), response.getStatusCode());
    }
}
