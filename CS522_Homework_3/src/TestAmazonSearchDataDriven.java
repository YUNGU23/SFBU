import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

public class TestAmazonSearchDataDriven {
    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;
    private final static Logger LOGGER = Logger.getLogger(TestAmazonSearchDataDriven.class.getName());
    private static final String CHROME_DRIVER_PATH = "C:\\Drivers\\Selenuim\\chromedriver.exe";
    private static final String AMAZON_URL = "https://www.amazon.com";
    private static final String CATEGORY = "Appliances";
    private static final String PRODUCT = "Oven";
    private static final String BEST_SELLERS_LINK = "Best Sellers";
    private static final String EBAY_URL = "https://www.ebay.com";

    public TestAmazonSearchDataDriven() {
        LOGGER.info("Initializing browser.");
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void launchBrowserGoToAmazon() {
        LOGGER.info("Navigating to Amazon.");
        driver.get(AMAZON_URL);
        driver.manage().window().maximize();
    }

    public void selectCategory(String category) {
        LOGGER.info("Selecting category: " + category);
        WebElement categoryDropdown = driver.findElement(By.id("searchDropdownBox"));
        categoryDropdown.sendKeys(category);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-search-submit-button"))).click();
    }

    public void searchProduct(String product) {
        LOGGER.info("Searching for product: " + product);
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys(product);
        searchBox.submit();
    }

    public void openLinkBestSellers() {
        LOGGER.info("Opening Best Sellers link.");
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(BEST_SELLERS_LINK))).click();
    }

    public void navigateToEbayGoBack() {
        LOGGER.info("Navigating to eBay.");
        driver.navigate().to(EBAY_URL);
        LOGGER.info("Navigating back to Amazon.");
        driver.navigate().back();
    }

    public void closeExit() {
        LOGGER.info("Closing browser.");
        driver.quit();
    }

    public void takeScreenshot(String screenshotName) throws IOException {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File DestFile = new File("./screenshots/" + screenshotName + "_" + timestamp + ".png");
        FileUtils.copyFile(SrcFile, DestFile);
    }

    public void useExcelData(String excelPath) throws Exception {
        FileInputStream fis = new FileInputStream(excelPath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell categoryCell = row.getCell(0);
            Cell productCell = row.getCell(1);
            if (categoryCell == null || productCell == null) break;
            String mySearchCategory = categoryCell.getStringCellValue();
            String mySearchItem = productCell.getStringCellValue();
            selectCategory(mySearchCategory);
            searchProduct(mySearchItem);
            takeScreenshot("search_" + mySearchCategory + "_" + mySearchItem);
        }
        workbook.close();
        fis.close();
    }

    public static void main(String[] args) {
        TestAmazonSearchDataDriven obj = new TestAmazonSearchDataDriven();
        try {
            obj.launchBrowserGoToAmazon();
            obj.useExcelData("D:\\gu\\SFBU\\CS522\\week12\\product.xlsx");
            obj.openLinkBestSellers();
            obj.navigateToEbayGoBack();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            obj.closeExit();
        }
    }
}
