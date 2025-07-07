import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.example",
        plugin = {"json:target/allure-results/cucumber.json", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        tags = "@UI or @API"
)

public class RunCucumberTest {
}
