package com.example.hooks;

import com.example.enums.BrowserEnum;
import com.example.enums.ExistUser;
import com.example.logging.LogConfigurator;
import com.example.playwrightManager.PlaywrightManager;
import com.example.screenshots.ScreenShotConfigurator;
import com.example.utils.ScenarioContext;
import io.cucumber.java.*;
import org.junit.jupiter.api.AfterEach;

public class ExecutionHook {
    private static final ThreadLocal<ScreenShotConfigurator> configuratorThreadLocal =
            ThreadLocal.withInitial(ScreenShotConfigurator::new);

    public ScreenShotConfigurator screenShotConfigurator() {
        return configuratorThreadLocal.get();
    }

    @Before("@UI")
    public void setupUI(Scenario scenario) {
        PlaywrightManager.getInstance().launchBrowser(BrowserEnum.CHROME.getKey(), BrowserEnum.HEADLESS_FALSE.getBoolean());
        PlaywrightManager.getInstance().openNewTab();
        ScenarioContext.getInstance().set("username", ExistUser.USERNAME.getKey());
        ScenarioContext.getInstance().set("email", ExistUser.EMAIL.getKey());
        ScenarioContext.getInstance().set("password", ExistUser.PASSWORD.getKey());
        new LogConfigurator().setupLogging(scenario, "UI");
    }

    @Before("@API")
    public void setupAPI(Scenario scenario) {
        new LogConfigurator().setupLogging(scenario, "API");
    }

    @After("@UI")
    public void afterUIScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            screenShotConfigurator().takeScreenshot(scenario, true);
            configuratorThreadLocal.remove();
        }

        PlaywrightManager.getInstance().cleanupScenario();
    }

    @AfterEach
    public void afterEachScenario() {
        PlaywrightManager.getInstance().close();
    }

    @AfterAll
    public static void tearDown() {
        PlaywrightManager.getInstance().closeAll();
    }
}
