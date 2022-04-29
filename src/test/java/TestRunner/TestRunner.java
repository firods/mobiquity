package TestRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.*;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources" }, glue = { "StepDefinations" }, plugin = {
		"json:target/cucumber.json", "timeline:test-output-thread/" })
public class TestRunner {
}
