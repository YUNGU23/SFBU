import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class TestRunner {
    WebDriver driver;
    String myWebBrowserDriver = "web-driver.chrome.driver";
    String myWebBrowserDriverPath = "C:\\Drivers\\Selenium\\chrome\\chromedriver.exe";
    String urlEbay = "http://www.ebay.com/";
    String urlAmazon = "http://www.amazon.com/";

    // Launch browser and go to amazon
    @Test(priority = 1)
    void launchBrowserGoToAmazon() throws Exception {
        System.setProperty(myWebBrowserDriver, myWebBrowserDriverPath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(urlAmazon);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        takeSnapShot("C:\\CS522Screenshots\\launchBrowserGoToAmazon");
    }

    // Maximize window size
    @Test(priority = 2)
    void maximizeWindowSize() {
        driver.manage().window().maximize();
    }

    // Select a search category
    @Test(priority = 3)
    void selectCategory() throws Exception {
        String selectedCategoryText = "Books";
        driver.findElement(By.id("searchDropdownBox")).click();
        Select categoryDropdown = new Select(driver.findElement(By.id("searchDropdownBox")));
        categoryDropdown.selectByVisibleText(selectedCategoryText);
        Thread.sleep(2000);
        takeSnapShot("C:\\CS522Screenshots\\selectCategory_" + selectedCategoryText);
    }

    // Input a product item and click Search
    @Test(priority = 4)
    void searchProduct() throws Exception {
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Harry Potter");
        driver.findElement(By.id("nav-search-submit-button")).click();
        takeSnapShot("C:\\CS522Screenshots\\searchProduct");
    }

    // Click the link Best Sellers
    @Test(priority = 5)
    void openLinkBestSellers() throws Exception {
        driver.findElement(By.linkText("Best Sellers")).click();
        Thread.sleep(2000);
        takeSnapShot("C:\\CS522Screenshots\\openLinkBestSellers");
    }

    // Navigate to ebay.com then go back
    @Test(priority = 6)
    void navigateToEbayGoBack() throws Exception {
        driver.navigate().to(urlEbay);
        Thread.sleep(2000);
        driver.navigate().back();
        Thread.sleep(2000);
        takeSnapShot("C:\\CS522Screenshots\\navigateToEbayGoBack");
    }

    // Close browser and exit
    @Test(priority = 7)
    void closeExit() {
        driver.close();
        driver.quit();
    }

    void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }

    public void takeSnapShot(String fileWithPath) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath + new
                SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png");
        FileUtils.copyFile(SrcFile, DestFile);
    }

    public static void main(String[] args) throws Exception {
        TestRunner obj = new TestRunner();

        obj.launchBrowserGoToAmazon();
        obj.maximizeWindowSize();
        obj.selectCategory();
        obj.searchProduct();
        obj.openLinkBestSellers();
        obj.navigateToEbayGoBack();
        obj.closeExit();
    }
}

