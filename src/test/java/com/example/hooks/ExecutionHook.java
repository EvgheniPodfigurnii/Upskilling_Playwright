package com.example.hooks;

import com.example.playwrightManager.PlaywrightManager;
import com.example.utils.ScenarioContext;
import io.cucumber.java.Before;
import io.cucumber.java.After;

public class ExecutionHook {

    @Before
    public void setUp() {
        PlaywrightManager.getInstance();
        ScenarioContext.getInstance();
    }

    @After
    public void tearDown() {
        PlaywrightManager.getInstance().close();
    }

//    @AfterStep
//    public void afterStep(Scenario scenario){
////        if(scenario.isFailed()){
//            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//            scenario.attach(screenshot, "image/png", "Failure Screenshot");
////        }
//    }
}
