import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

public class StepDefinitions {
    TestAmazonSearchDataDriven obj = new TestAmazonSearchDataDriven();

    @Given("I am on the Amazon homepage")
    public void i_am_on_the_amazon_homepage() {
        obj.launchBrowserGoToAmazon(TestAmazonSearchDataDriven.amazonUrl);
        obj.maximizeWindowSize();
    }

    @When("I select the {string} category")
    public void i_select_the_category(String category) {
        obj.selectCategory(category);
    }

    @When("I search for {string}")
    public void i_search_for(String product) {
        obj.searchProduct(product);
    }

    @Then("I take a screenshot {string}")
    public void i_take_a_screenshot(String screenshotName) throws IOException {
        obj.takeScreenshot(screenshotName);
    }

    @When("I click on {string}")
    public void i_click_on(String bestsellers) {
        obj.openLinkBestSellers(bestsellers);
    }

    @When("I navigate to eBay and come back")
    public void i_navigate_to_ebay_and_come_back() {
        obj.navigateToEbayGoBack(TestAmazonSearchDataDriven.ebay);
    }

    @Then("I close the browser")
    public void i_close_the_browser() {
        obj.closeExit();
    }
}
