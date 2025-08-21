package com.example.stepsDefinition.api;

import com.example.enums.APIEndPoint;
import com.example.helper.CommonMethods;
import com.example.configurations.ConfigLoader;
import com.example.pages.api.ApiLogin;
import com.example.utils.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiLoginStepDefinition {
    private static final Logger logger = LogManager.getLogger();
    private final CommonMethods commonMethods = new CommonMethods();
    private final ScenarioContext scenarioContext = ScenarioContext.getInstance();
    private final ApiLogin apiLogin = new ApiLogin();

    @Given("The API endpoint is {string}")
    public void the_api_endpoint_is(String endPointName) {
        APIEndPoint apiEndPoint = APIEndPoint.valueOf(endPointName.toUpperCase());
        String endpoint = String.format("%s%s", ConfigLoader.getProperty("base.url"), APIEndPoint.getAPIEndPoint(apiEndPoint));
        scenarioContext.set("apiEndPoint", endpoint);

        logger.info("API endpoint is: {}", endpoint);
        Allure.step(String.format("API endpoint is: %s", endpoint));
    }

    @When("Send a POST request with {string} and {string}")
    public void send_a_post_request_with_email_and_password(String email, String password) {
        Map<String, String> params = new HashMap<>();

        if (email.isEmpty()) {
            params.put("password", password);
        } else if (password.isEmpty()) {
            params.put("email", email);
        } else {
            params.put("email", email);
            params.put("password", password);
        }

        Response response = RestAssured.given()
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .formParams(params)
                .when()
                .post(scenarioContext.get("apiEndPoint"));

        scenarioContext.set("responseCode", String.valueOf(response));
        scenarioContext.set("responseGetBody", String.valueOf(response.getBody().asString()));

        logger.info("Response after POST request with email: {} and password: {} is : {}",email, password, response.getStatusCode());
        Allure.step(String.format("Response after POST request with email: %s and password: %s is : %s",email, password, response.getStatusCode()));
    }

    @When("Create new user account")
    public void create_new_user_account() {
        Map<String, String> params = apiLogin.generateUserParams(false);

        Response response = RestAssured.given()
                .formParams(params)
                .when()
                .post(scenarioContext.get("apiEndPoint"));

        scenarioContext.set("responseCode", String.valueOf(response));
        scenarioContext.set("responseGetBody", String.valueOf(response.getBody().asString()));

        logger.info("Response after Create new user account is : {}", response.getStatusCode());
        Allure.step(String.format("Response after Create new user account is : %s", response.getStatusCode()));
    }

    @When("Create new user account for DELETE flow")
    public void create_new_user_account_delete_flow() {
        Map<String, String> params = apiLogin.generateUserParams(false);

        Response response = RestAssured.given()
                .formParams(params)
                .when()
                .post(scenarioContext.get("apiEndPoint"));

        scenarioContext.set("responseCode", String.valueOf(response));
        scenarioContext.set("responseGetBody", String.valueOf(response.getBody().asString()));

        scenarioContext.set("apiEmail", params.get("email"));
        scenarioContext.set("apiPassword", params.get("password"));

        logger.info("Response after Create new user account for DELETE flow is : {}", response.getStatusCode());
        Allure.step(String.format("Response after Create new user account for DELETE flow is : %s", response.getStatusCode()));
    }

    @And("DELETE user account")
    public void delete_user_account() {
        Map<String, String> params = new HashMap<>();
        params.put("email", scenarioContext.get("apiEmail"));
        params.put("password", scenarioContext.get("apiPassword"));

        Response response = RestAssured.given()
                .formParams(params)
                .when()
                .delete(scenarioContext.get("apiEndPoint"));

        scenarioContext.set("responseCode", String.valueOf(response));
        scenarioContext.set("responseGetBody", String.valueOf(response.getBody().asString()));

        logger.info("Response after DELETE user account is : {}", response.getStatusCode());
        Allure.step(String.format("Response after DELETE user account is : %s", response.getStatusCode()));
    }

    @When("Create new user account for UPDATE flow")
    public void create_new_user_account_update_flow() {
        Map<String, String> params = apiLogin.generateUserParams(true);

        Response response = RestAssured.given()
                .formParams(params)
                .when()
                .post(scenarioContext.get("apiEndPoint"));

        scenarioContext.set("responseCode", String.valueOf(response));
        scenarioContext.set("responseGetBody", String.valueOf(response.getBody().asString()));

        logger.info("Response after Create new user account for UPDATE flow is : {}", response.getStatusCode());
        Allure.step(String.format("Response after Create new user account for UPDATE flow is : %s", response.getStatusCode()));
    }

    @When("Get user details")
    public void get_user_details() {
        Response response = RestAssured.given()
                .queryParam("email", scenarioContext.get("email"))
                .when()
                .get(scenarioContext.get("apiEndPoint"));

        scenarioContext.set("responseCode", String.valueOf(response));
        scenarioContext.set("responseGetBody", String.valueOf(response.getBody().asString()));

        logger.info("Response after Get user details is : {}", response.getStatusCode());
        Allure.step(String.format("Response after Get user details is : %s", response.getStatusCode()));
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

        Response response = RestAssured.given()
                .formParams(params)
                .when()
                .put(scenarioContext.get("apiEndPoint"));

        scenarioContext.set("responseCode", String.valueOf(response));
        scenarioContext.set("responseGetBody", String.valueOf(response.getBody().asString()));

        logger.info("Response after Update user account is : {}", response.getStatusCode());
        Allure.step(String.format("Response after Update user account is : %s", response.getStatusCode()));
    }

    @Then("The response code from JSON should be {int}")
    public void the_response_code_should_be(int expectedResponseCode) {
        String responseBody = scenarioContext.get("responseGetBody");
        int actualResponseCode = Integer.parseInt(commonMethods.getValueFromJson(responseBody, "responseCode"));

        if (actualResponseCode == expectedResponseCode) {
            logger.info("The response code from JSON: {}", actualResponseCode);
            Allure.step(String.format("The response code from JSON: %s", actualResponseCode));
        } else {
            assertThat(actualResponseCode)
                    .withFailMessage(() -> {
                        logger.error("Expected response code: {}, but got: {}", expectedResponseCode, actualResponseCode);
                        Allure.step(String.format("Expected response code: %s, but got: %s", expectedResponseCode, actualResponseCode));
                        return String.format("Expected response code: %s, but got: %s", expectedResponseCode, actualResponseCode);
                    })
                    .isEqualTo(expectedResponseCode);
        }
    }

    @And("The response message from JSON should be {string}")
    public void the_response_message_should_be(String expectedMessage) {
        String responseBody = scenarioContext.get("responseGetBody");
        String actualResponseMessage = commonMethods.getValueFromJson(responseBody, "message");

        if (actualResponseMessage.equalsIgnoreCase(expectedMessage)) {
            logger.info("Response message from JSON : {}", actualResponseMessage);
            Allure.step(String.format("Response message from JSON : %s", actualResponseMessage));
        } else {
            assertThat(actualResponseMessage)
                    .withFailMessage(() -> {
                        logger.error("Expected message: {}, but got: {}", expectedMessage, actualResponseMessage);
                        Allure.step(String.format("Expected message: %s, but got: %s", expectedMessage, actualResponseMessage));
                        return String.format("Expected message: %s, but got: %s", expectedMessage, actualResponseMessage);
                    })
                    .isEqualTo(expectedMessage);
        }
    }

    @And("JSON should be contains {string} details")
    public void json_user_details(String object) {
        String responseBody = scenarioContext.get("responseGetBody");
        String actualResponseMessage = commonMethods.getValueFromJson(responseBody, object);

        if (!actualResponseMessage.isEmpty()) {
            logger.info("Response message from JSON contains {} details : {}", object, actualResponseMessage);
            Allure.step(String.format("Response message from JSON contains %s details : %s", object, actualResponseMessage));
        } else {
            logger.error("JSON returned null");
            Allure.step("JSON returned null");
        }
    }

    @When("Send {string} request")
    public void send_request(String method) {
        Response response = switch (method.toUpperCase()) {
            case "GET" -> RestAssured.given()
                    .when()
                    .get(scenarioContext.get("apiEndPoint"));
            case "POST" -> RestAssured.given()
                    .when()
                    .post(scenarioContext.get("apiEndPoint"));
            default -> throw new IllegalArgumentException("Unsupported method: " + method);
        };

        scenarioContext.set("responseCode", String.valueOf(response));
        scenarioContext.set("responseGetBody", String.valueOf(response.getBody().asString()));

        logger.info("{} request without parameters was successfully sent. Status: {}", method.toUpperCase(), response.getStatusCode());
        Allure.step(String.format("%s request without parameters was successfully sent. Status: %s", method.toUpperCase(), response.getStatusCode()));
    }
}
