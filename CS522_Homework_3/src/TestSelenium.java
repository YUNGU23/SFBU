import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class TestSelenium {
    public static void main(String[] args) throws InterruptedException {
        // Set the system property for the Chrome WebDriver
        System.setProperty("web-driver.chrome.driver","C:\\Drivers\\Selenium\\chrome\\chromedriver.exe");

        // Create a new instance of ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Create an instance of Actions class
        Actions actions = new Actions(driver);

        // Open the Google homepage
        driver.get("http://www.google.com");

        // Maximize the browser window
        driver.manage().window().maximize();

        // Pause execution for 2 seconds
        Thread.sleep(2000);

        // Find the search input field and enter "SFBU"
        driver.findElement(By.name("q")).sendKeys("SFBU");

        // Perform a keyboard action to submit the search query
        actions.sendKeys(Keys.ENTER).build().perform();

        // Pause execution for 2 seconds
        Thread.sleep(2000);

        // Navigate to "https://sfbu.edu"
        driver.navigate().to("https://sfbu.edu");
        Thread.sleep(2000);

        // Store the expected and actual page titles
        String expectedTitle = "SFBU | San Francisco Bay University | SFBU";
        String actualTitle = driver.getTitle();

        // Print the current open web page title
        System.out.println("Current open web page title is " + actualTitle);

        // Compare the expected and actual page titles and print the result
        if (actualTitle.contentEquals(expectedTitle)){
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed");
        }

        // Close the current browser window and terminate the WebDriver session and close the browser
        driver.close();
        driver.quit();
    }
}
