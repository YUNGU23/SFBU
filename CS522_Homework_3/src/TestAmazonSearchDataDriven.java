import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

public class TestAmazonSearchDataDriven {
    WebDriver driver;
    Actions actions;
    String driverPath = "C:\\Drivers\\Selenuim\\chromedriver.exe";
    static String amazonUrl = "https://www.amazon.com";
    static String bestsellers = "Best Sellers";
    static String ebay = "https://www.ebay.com";

    void launchBrowserGoToAmazon(String url) {
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        actions = new Actions(driver);
        driver.get(url);
    }

    void maximizeWindowSize() {
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    void selectCategory(String category) {
        WebElement categoryDropdown = driver.findElement(By.id("searchDropdownBox"));
        categoryDropdown.sendKeys(category);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeContains(categoryDropdown, "value", category));
        captureScreenshot("CategorySelected");
    }

    void searchProduct(String product) {
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys(product);
        searchBox.submit();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains(product));
        captureScreenshot("ProductSearched");
    }

    void openLinkBestSellers(String bestsellers) {
        driver.findElement(By.linkText(bestsellers)).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("bestsellers"));
        captureScreenshot("BestSellersOpened");
    }

    void navigateToEbayGoBack(String ebay) {
        driver.navigate().to(ebay);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("ebay"));
        driver.navigate().back();
        wait.until(ExpectedConditions.urlContains("amazon"));
        captureScreenshot("NavigatedToEbayAndBack");
    }

    void closeExit() {
        driver.quit();
    }

    void captureScreenshot(String screenshotName) {
        try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotFileName = screenshotName + "_" + timestamp + ".png";
            File destinationFile = new File(screenshotFileName);
            FileUtils.copyFile(screenshotFile, destinationFile);
            System.out.println("Screenshot saved as: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestAmazonSearchDataDriven obj = new TestAmazonSearchDataDriven();
        obj.launchBrowserGoToAmazon(amazonUrl);
        obj.maximizeWindowSize();

        // Read data from Excel and perform actions
        try {
            FileInputStream fis = new FileInputStream("D:\\gu\\SFBU\\CS522\\week12\\product.xlsx");
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String category = row.getCell(0).getStringCellValue();
                String product = row.getCell(1).getStringCellValue();

                obj.selectCategory(category);
                obj.searchProduct(product);
                obj.openLinkBestSellers(bestsellers);
                obj.navigateToEbayGoBack(ebay);
            }

            workbook.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        obj.closeExit();
    }

}
