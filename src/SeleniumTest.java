
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class SeleniumTest {

    @Test
    public void seleniumAssessment(){
        //System.setProperty("webdriver.https.factory", "jdk-https-client");
        // Navigate to https://pnfp.myapexcard.com/info
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Open a website

        driver.get("https://pnfp.myapexcard.com/info");

        // Click on the “View Business Credit Cards” link
        // Click “+Terms &amp; Conditions” for card type: Mastercard® Business Platinum Rewards
        // Assert that the APR for Cash Advances is less than 24%
    }
}
