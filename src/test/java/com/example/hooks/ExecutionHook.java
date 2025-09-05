package com.example.hooks;

import com.example.enums.BrowserEnum;
import com.example.enums.ExistUser;
import com.example.logging.LogConfigurator;
import com.example.playwrightManager.PlaywrightManager;
import com.example.screenshots.ScreenShotConfigurator;
import com.example.utils.ScenarioContext;
import io.cucumber.java.*;

public class ExecutionHook {
    private final String browserName = BrowserEnum.FIREFOX.name();
    private final boolean headless = BrowserEnum.HEADLESS_FALSE.getBoolean();

    private static final ThreadLocal<ScreenShotConfigurator> configuratorThreadLocal =
            ThreadLocal.withInitial(ScreenShotConfigurator::new);

    private ScreenShotConfigurator screenShotConfigurator() {
        return configuratorThreadLocal.get();
    }

    @Before("@UI")
    public void setupUI(Scenario scenario) {
        PlaywrightManager pm = PlaywrightManager.getInstance();

        // Per-thread browser/context (idempotent)
        pm.launchBrowser(browserName, headless);

        // Mark thread busy for this scenario + open a NEW tab (existing tabs remain open)
        pm.beginScenario();
        pm.openNewTab();

        // Scenario-scoped test data & logging
        ScenarioContext.getInstance().set("username", ExistUser.USERNAME.getKey());
        ScenarioContext.getInstance().set("email",    ExistUser.EMAIL.getKey());
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
        }
        configuratorThreadLocal.remove();

        // Keep tab open, clear cookies, rotate this thread's browser if cap reached, mark thread idle
        PlaywrightManager.getInstance().endScenarioDontCloseTab(browserName, headless);
    }

    @AfterAll
    public static void tearDown() {
        // Closes only idle threads; any thread still running a long scenario stays alive.
        PlaywrightManager.closeAllIdle();
        // Remaining busy threads will be closed by the JVM shutdown hook once they finish.
    }
}
