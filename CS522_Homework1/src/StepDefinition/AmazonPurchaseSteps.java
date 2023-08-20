package StepDefinition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.*;
import io.cucumber.java.Before;
import io.cucumber.java.After;

public class AmazonPurchaseSteps {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Initialize WebDriver and other setup
    }

    @Given("I open the Amazon website")
    public void openAmazonWebsite() {
        // This method will be used as a step definition
    }

    @When("I search for {string}")
    public void searchForProduct(String product) {
        // Search for the specified product
    }

    @And("I select the product {string}")
    public void selectProduct(String product) {
        // Select the specified product
    }

    @And("I compare the prices")
    public void comparePrices() {
        // Compare the prices of the product
    }

    @And("I add the product to cart")
    public void addToCart() {
        // Add the product to the cart
    }

    @Then("I should see the product added to cart")
    public void verifyProductAddedToCart() {
        // Verify the product is added to the cart
    }

    @After
    public void tearDown() {
        // Quit WebDriver and perform cleanup
    }
}
