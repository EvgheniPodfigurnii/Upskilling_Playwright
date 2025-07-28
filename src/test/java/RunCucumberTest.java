import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@SelectClasspathResource("features")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@UI or @API")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "json:target/allure-results/cucumber.json,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class RunCucumberTest {
}