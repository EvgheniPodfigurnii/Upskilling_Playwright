import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
//        glue = "com.example.stepsDefinition",
        glue = "com.example",
        plugin = {"pretty", "html:target/cucumber-report.html"},
//        monochrome = true,
//        tags = "@Run and @UI"
//        tags = "@Run and @API and @11111"
        tags = "@Run and @sss and @1111 and @UI"
//        tags = "@8071"
)

public class RunCucumberTest {
}
