package StepDefinition;

import io.cucumber.java.en.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class TestAmazonSearchSteps {
    private WebDriver driver;
    private final String myWebBrowserDriverPath = "C:\\Drivers\\Selenium\\chrome\\chromedriver.exe";
    private final String urlEbay = "http://www.ebay.com/";
    private final String urlAmazon = "http://www.amazon.com/";

    @Given("Open the Chrome and launch the Amazon application")
    public void launchBrowserGoToAmazon() {
        setupWebDriver();
        driver.get(urlAmazon);
    }

    @When("Maximize window size")
    public void maximizeWindowSize() {
        driver.manage().window().maximize();
    }

    @When("Input a product item name as {string} and click Search")
    public void searchProduct(String productName) {
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys(productName);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement submitButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-search-submit-button")));
        submitButton.click();
    }

    @When("Click the link Best Sellers")
    public void openLinkBestSellers() {
        driver.findElement(By.linkText("Best Sellers")).click();
    }

    @When("Navigate to ebay.com then go back")
    public void navigateToEbayGoBack() {
        driver.navigate().to(urlEbay);
        driver.navigate().back();
    }

    @Then("Take a screenshot with file name {string}")
    public void takeSnapShot(String fileName) throws Exception {
        TakesScreenshot scrShot = (TakesScreenshot) driver;
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
        String date = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
        File destFile = new File("C:\\CS522Screenshots\\" + fileName + "_" + date + ".png");
        FileUtils.copyFile(srcFile, destFile);
    }

    @Then("Close browser and exit")
    public void closeExit() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void setupWebDriver() {
        System.setProperty("web-driver.chrome.driver", myWebBrowserDriverPath);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}
