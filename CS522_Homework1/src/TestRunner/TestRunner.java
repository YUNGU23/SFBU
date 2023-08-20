package TestRunner;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/amazon_purchase.feature",
        glue = "path.to.step.definitions",
        plugin = {"pretty", "html:target/cucumber-reports"})
public class TestRunner extends AbstractTestNGCucumberTests {
    // Empty class, TestNG will execute the Cucumber tests
}
