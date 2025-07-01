import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.example",
        plugin = {"pretty", "html:target/cucumber-report.html"},
        tags = "@UI or @API"
)

public class RunCucumberTest {
}
