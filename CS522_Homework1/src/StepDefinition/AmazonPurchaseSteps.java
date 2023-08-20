package StepDefinition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class AmazonPurchaseSteps {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Initialize WebDriver and other setup
    }

    @BeforeMethod
    public void openAmazonWebsite() {
        // Open Amazon website
    }

    @Given("I open the Amazon website")
    public void openAmazonWebsite() {
        // Open Amazon website
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

    @AfterClass
    public void tearDown() {
        // Quit WebDriver and perform cleanup
    }
}
