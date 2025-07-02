package com.example.hooks;

import com.example.enums.BrowserEnum;
import com.example.enums.ExistUser;
import com.example.logging.LogConfigurator;
import com.example.playwrightManager.PlaywrightManager;
import com.example.screenshots.ScreenShotConfigurator;
import com.example.utils.ScenarioContext;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;


public class ExecutionHook {

    public ScreenShotConfigurator screenShotConfigurator() {
        return new ScreenShotConfigurator();
    }

    @Before("@UI")
    public void setupUI(Scenario scenario) {
        PlaywrightManager.getInstance().LaunchBrowser(BrowserEnum.EDGE.getKey());

        ScenarioContext.getInstance().setExistUser("username", ExistUser.USERNAME.getKey());
        ScenarioContext.getInstance().setExistUser("email", ExistUser.EMAIL.getKey());
        ScenarioContext.getInstance().setExistUser("password", ExistUser.PASSWORD.getKey());

        LogConfigurator.getLoggingPath(scenario, "UI");
    }

    @Before("@API")
    public void setupAPI(Scenario scenario) {
        LogConfigurator.getLoggingPath(scenario, "API");
    }

    @After(value = "@UI", order = 2)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            screenShotConfigurator().takeScreenshot();
        }
    }

    @After(value = "@UI", order = 1)
    public void tearDown() {
            PlaywrightManager.getInstance().close();
    }
}
