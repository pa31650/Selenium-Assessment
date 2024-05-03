
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;

public class SeleniumTest {

    @BeforeTest
    public void BeforeTest(){

    }
    @Test
    public void seleniumAssessment(){
        // Navigate to https://pnfp.myapexcard.com/info
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver();
        Wait<WebDriver> wait =
                new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofMillis(300))
                        .ignoring(NoSuchElementException.class);

        // Open a website
        driver.get("https://pnfp.myapexcard.com/info");

        // Click on the “View Business Credit Cards” link
        wait.until(t -> driver.findElement(By.id("productSelector_categorySwitch_text")));
        var categoryText = driver.findElement(By.id("productSelector_categorySwitch_text"));
        if (categoryText.getText().contains("Personal Credit Cards")){
            wait.until(d -> driver.findElement(By.xpath("//div[@class='productSelector_category_business']")).isDisplayed());
            var bizlink = driver.findElement(By.xpath("//div[@class='productSelector_category_business']"));
            bizlink.click();
        }

        // Click “+Terms &amp; Conditions” for card type: Mastercard® Business Platinum Rewards
        var card = driver.findElement(By.xpath("//span[text()[contains(.,'Business Platinum Rewards')]]//ancestor::div[@data-app-group]"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", card);

        var link = card.findElement(By.xpath("//a[@href='#p=application/terms']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);

        //switch to new tab
        ArrayList<String> newTb = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(newTb.get(1));

        wait.until(ExpectedConditions.titleContains("Terms"));
        wait.until(d -> driver.findElement(By.xpath("//td//b[text()[contains(.,'APR for Cash Advances')]]/parent::td/following-sibling::td/b")).isDisplayed());

        // Assert that the APR for Cash Advances is less than 24%
        var rate = driver.findElement(By.xpath("//td//b[text()[contains(.,'APR for Cash Advances')]]/parent::td/following-sibling::td/b")).getText();
        double CaRate = Double.parseDouble(rate.replace("%", ""));
        double maxRate = 24.0;
        Assert.assertTrue(CaRate < maxRate);

        driver.quit();
    }
}
