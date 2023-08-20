import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class TestAmazonSearchDataDriven {
    // Driver settings
    private static final String WEB_BROWSER_DRIVER = "webd-river.chrome.driver";
    private static final String WEB_BROWSER_DRIVER_PATH = "C:\\Drivers\\Selenium\\chrome\\chromedriver.exe";

    // URLs and Links
    private static final String AMAZON_URL = "http://www.amazon.com/";
    private static final String BACK_TO_SCHOOL_LINK = "Back to School";

    // Element IDs
    private static final String SEARCH_BOX_ID = "twotabsearchtextbox";
    private static final String SUBMIT_BUTTON_ID = "nav-search-submit-button";
    private static final String CATEGORY_DROPDOWN_ID = "searchDropdownBox";

    // Element Selectors
    private static final String PRODUCT_CSS_SELECTOR = "span.a-size-base.a-color-base";
    private static final String FIRST_PRODUCT_CSS_SELECTOR = ".s-main-slot .s-result-item span.a-text-normal";
    private static final String QTY_LABEL_CSS_SELECTOR = "span.a-dropdown-label";
    private static final String ADD_TO_CART_BUTTON_ID = "add-to-cart-button";

    // Logger
    private final static Logger LOGGER = Logger.getLogger(TestAmazonSearchDataDriven.class.getName());

    // Other fields
    WebDriver driver;
    WebDriverWait wait;
    // Launch browser and go to Amazon
    public void launchBrowserGoToAmazon() throws Exception {
        LOGGER.info("Launching browser and navigating to Amazon.");
        System.setProperty(WEB_BROWSER_DRIVER, WEB_BROWSER_DRIVER_PATH);
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get(AMAZON_URL);
        takeSnapShot("./screenshots/launchBrowserGoToAmazon");
    }

    // Maximize window size
    public void maximizeWindowSize() throws Exception {
        LOGGER.info("Maximizing window.");
        driver.manage().window().maximize();
        takeSnapShot("./screenshots/maximizeWindowSize");
    }

    // Input a product item and click Search
    public void searchProduct(String mySearchItem) throws Exception {
        LOGGER.info("Searching for product: " + mySearchItem);
        driver.findElement(By.id(SEARCH_BOX_ID)).sendKeys(mySearchItem);
        driver.findElement(By.id(SUBMIT_BUTTON_ID)).click();
        takeSnapShot("./screenshots/searchProduct_" + mySearchItem);
    }

    // Select a search category
    public void selectCategory(String mySearchCategory) throws Exception {
        LOGGER.info("Selecting category: " + mySearchCategory);
        Select categoryDropdown = new Select(driver.findElement(By.id(CATEGORY_DROPDOWN_ID)));
        categoryDropdown.selectByVisibleText(mySearchCategory);
        takeSnapShot("./screenshots/selectCategory_" + mySearchCategory);
    }

    // Find a product by filter
    public void findProduct(String filter) throws Exception {
        LOGGER.info("findProductBy: " + filter);
        Thread.sleep(3000);
        List<WebElement> products = driver.findElements(By.cssSelector(PRODUCT_CSS_SELECTOR));
        WebElement targetProduct = null;
        for (WebElement product : products) {
            if (product.getText().contains(filter)) {
                targetProduct = product;
                break;
            }
        }

        if (targetProduct == null) {
            throw new Exception("Target product not found");
        }
        targetProduct.click();
        takeSnapShot("./screenshots/findProduct");
    }

    // Open product details
    public void openDetail() throws Exception {
        WebElement firstProductLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(FIRST_PRODUCT_CSS_SELECTOR)));
        firstProductLink.click();

        takeSnapShot("./screenshots/openDetails");
    }

    public void selectQty(int qty) throws Exception {
        WebElement qtyLabel = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(QTY_LABEL_CSS_SELECTOR)));
        qtyLabel.click();

        WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-value='{\"stringVal\":\"" + qty + "\"}']")));
        option2.click();

        takeSnapShot("./screenshots/openDetailsAndSelectQty_" + qty);
    }
    public void openLinkBackToSchool() throws Exception {
        LOGGER.info("Opening Link Back To School.");
        WebElement linkElement = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(BACK_TO_SCHOOL_LINK)));
        linkElement.click();
        takeSnapShot("./screenshots/openLinkBackToSchool");
    }

    // Add to cart
    public void addToCart() throws Exception {
        driver.findElement(By.id(ADD_TO_CART_BUTTON_ID)).click();
        takeSnapShot("./screenshots/addToCart");
    }

    // Close browser and exit
    public void closeExit() {
        LOGGER.info("Closing browser.");
        driver.quit();
    }

    public void navigationBack() {
        LOGGER.info("navigationBack");
        driver.navigate().back();
    }

    // Capture screenshots
    public void takeSnapShot(String fileWithPath) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png");
        FileUtils.copyFile(SrcFile, DestFile);
    }

    // Read Excel data
    public void useExcelData(String Path) throws Exception {
        FileInputStream fis = new FileInputStream(Path);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();

        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell categoryCell = row.getCell(0);
            Cell productCell = row.getCell(1);

            if (categoryCell == null || productCell == null) break;
            String mySearchCategory = categoryCell.getStringCellValue();
            String mySearchItem = productCell.getStringCellValue();

            selectCategory(mySearchCategory);
            searchProduct(mySearchItem);
        }

        workbook.close();
        fis.close();
    }

    @BeforeTest
    public void setUp() throws Exception {
        launchBrowserGoToAmazon();
        maximizeWindowSize();
    }

    @Test
    public void testUseExcelData() throws Exception {
        useExcelData("/Users/dang/Desktop/CS522_Homework/products.xls");
    }

    @Test(dependsOnMethods = "testUseExcelData")
    public void testFindProductByModel() throws Exception {
        findProduct("Apple");
    }

    @Test(dependsOnMethods = "testFindProductByModel")
    public void testFindProductByPrice() throws Exception {
        findProduct("$200");
    }

    @Test(dependsOnMethods = "testFindProductByPrice")
    public void testOpenDetail() throws Exception {
        openDetail();
    }

    @Test(dependsOnMethods = "testOpenDetail")
    public void testSelectQty() throws Exception {
        selectQty(2);
    }

    @Test(dependsOnMethods = "testSelectQty")
    public void testOpenLinkBackToSchool() throws Exception {
        openLinkBackToSchool();
    }

    @Test(dependsOnMethods = "testOpenLinkBackToSchool")
    public void testNavigationBack() {
        navigationBack();
    }

    @Test(dependsOnMethods = "testNavigationBack")
    public void testAddToCart() throws Exception {
        addToCart();
    }

    @AfterTest
    public void tearDown() {
        closeExit();
    }
}
