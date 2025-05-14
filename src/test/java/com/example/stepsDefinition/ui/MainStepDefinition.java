package com.example.stepsDefinition.ui;

import com.example.pages.ui.MainPage;
import com.example.playwrightManager.PlaywrightManager;
import io.cucumber.java.en.And;
import java.util.Objects;

public class MainStepDefinition {
    PlaywrightManager driver;
    MainPage mainPage;

    public MainStepDefinition(PlaywrightManager driverManager) {
        this.driver = Objects.requireNonNull(driverManager);
        this.mainPage = new MainPage(driver);
    }

//    @And("The main page has been uploaded")
//    public void the_main_page_has_been_uploaded() {
//        mainPage.checkThatPageIsVisible();
//    }
}
