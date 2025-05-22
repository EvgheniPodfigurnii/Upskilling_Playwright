package com.example.pages.api;

import com.example.dataFaker.DataFaker;
import com.example.utils.ScenarioContext;

import java.util.HashMap;
import java.util.Map;

public class ApiLoginPage {
    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    DataFaker dataFaker = new DataFaker();

    public Map<String, String> generateUserParams(boolean forScenarioContext) {
        Map<String, String> params = new HashMap<>();
        params.put("name", dataFaker.createName());
        params.put("email", dataFaker.createEmail());
        params.put("password", dataFaker.createPassword());
        params.put("title", "Mrs");
        params.put("birth_date", dataFaker.createBirthday("day"));
        params.put("birth_month", dataFaker.createBirthday("month"));
        params.put("birth_year", dataFaker.createBirthday("year"));
        params.put("firstname", dataFaker.createFirstName());
        params.put("lastname", dataFaker.createLastName());
        params.put("company", dataFaker.createCompany());
        params.put("address1", dataFaker.createAddress());
        params.put("address2", dataFaker.createAddress2());
        params.put("country", "Canada");
        params.put("zipcode", dataFaker.createZipCode());
        params.put("state", dataFaker.createState());
        params.put("city", dataFaker.createCity());
        params.put("mobile_number", dataFaker.createMobilePhone());

        if (forScenarioContext) {
            for (String key : params.keySet()) {
                scenarioContext.set("api" + key, params.get(key));
            }
        }

        return params;
    }
}
