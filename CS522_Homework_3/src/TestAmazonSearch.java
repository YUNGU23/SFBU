import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class TestAmazonSearch {
    // Global WebDriver definition
    WebDriver driver;
    Actions  actions;
    String driverPath = "C:\\Drivers\\Selenuim\\chromedriver.exe";
    static String amazonUrl = "https://www.amazon.com";
    static  String category = "Appliances";
    static  String product ="Oven";
    static  String bestsellers = "Best Sellers";
    static  String ebay = "https://www.ebay.com";

    // Navigate to Amazon
    void launchBrowserGoToAmazon( String url) {
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        actions = new Actions(driver);
        driver.get(url);
    }
    // Maximize window size
    void maximizeWindowSize() throws InterruptedException {
        driver.manage().window().maximize();
        Thread.sleep(2000);
    }
    //Select a search category;
    void selectCategory(String category) throws InterruptedException {
        // Locate and click on a search category
        WebElement categoryDropdown = driver.findElement(By.id("searchDropdownBox"));
        Thread.sleep(2000);
        categoryDropdown.sendKeys(category);
        Thread.sleep( 2000);
    }
    // Input a product item and click Search
    void searchProduct(String product) throws InterruptedException {
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        Thread.sleep(2000);
        searchBox.sendKeys(product);
        Thread.sleep(2000);
        searchBox.submit();
        Thread.sleep(2000);
    }

    // Click the link Best Sellers
    void openLinkBestSellers(String bestsellers)  throws  InterruptedException{
        driver.findElement(By.linkText(bestsellers)).click();
        Thread.sleep(2000);
    }
    // Navigate to eBay and Go back to Amazon
    void navigateToEbayGoBack(String ebay) throws InterruptedException{
        driver.navigate().to(ebay);
        Thread.sleep(2000);
        driver.navigate().back();
        Thread.sleep(2000);
    }
    // Close the browser and exit
    void closeExit() {
        driver.quit();
    }

    public static void main(String[] args) throws InterruptedException {
        TestAmazonSearch obj = new TestAmazonSearch();
        obj.launchBrowserGoToAmazon(amazonUrl);
        obj.maximizeWindowSize();
        obj.selectCategory(category);
        obj.searchProduct(product);
        obj.openLinkBestSellers(bestsellers);
        obj.navigateToEbayGoBack(ebay);
        obj.closeExit();
    }
}
