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
    private static final ThreadLocal<ScreenShotConfigurator> configuratorThreadLocal =
            ThreadLocal.withInitial(ScreenShotConfigurator::new);

    public ScreenShotConfigurator screenShotConfigurator() {
        return configuratorThreadLocal.get();
    }

    @Before("@UI")
    public void setupUI(Scenario scenario) {
        PlaywrightManager.getInstance().LaunchBrowser(BrowserEnum.CHROME.getKey(), BrowserEnum.HEADLESS_TRUE.getBoolean());
        ScenarioContext.getInstance().set("username", ExistUser.USERNAME.getKey());
        ScenarioContext.getInstance().set("email", ExistUser.EMAIL.getKey());
        ScenarioContext.getInstance().set("password", ExistUser.PASSWORD.getKey());
        new LogConfigurator().setupLogging(scenario, "UI");
    }

    @Before("@API")
    public void setupAPI(Scenario scenario) {
        ScenarioContext.getInstance();
        new LogConfigurator().setupLogging(scenario, "API");
    }

    @After
    public void cleanForMemoryLeaks() {
        new LogConfigurator().clearContext();
        configuratorThreadLocal.remove();
    }

    @After(value = "@UI", order = 2)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            screenShotConfigurator().takeScreenshot(scenario, true);
        }
    }

    @After(value = "@UI", order = 1)
    public void tearDown() {
            PlaywrightManager.getInstance().close();
    }
}
