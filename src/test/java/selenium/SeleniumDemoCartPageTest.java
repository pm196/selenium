package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumDemoCartPageTest {

    private WebDriver webDriver;
    private final String SELENIUM_BASE_URL = "http://seleniumdemo.com/";


    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        webDriver.quit();
    }

    @Test
    public void testEmptyCart() {
        // given
        webDriver.get(SELENIUM_BASE_URL);
        String expectedRedirectedUrl = "http://seleniumdemo.com/?page_id=5";

        // when
        WebElement cartWebElement = webDriver.findElement(By.xpath("//span[text()='Cart']"));
        cartWebElement.click();
        String redirectedUrl = webDriver.getCurrentUrl();
        WebElement emptyCart = webDriver.findElement(By.className("cart-empty"));

        // then
        Assertions.assertEquals(expectedRedirectedUrl, redirectedUrl);
        Assertions.assertTrue(emptyCart.isDisplayed());

    }

    @Test
    public void testAddingToCartOneElement() {
        // given
        webDriver.get(SELENIUM_BASE_URL);
        String expectedRedirectedCartUrl = "http://seleniumdemo.com/?page_id=5";
        String expectedRedirectedShopUrl = "http://seleniumdemo.com/?post_type=product";

        // when
        WebElement cartWebElement = webDriver.findElement(By.xpath("//span[text()='Shop']"));
        cartWebElement.click();
        String redirectedShopUrl = webDriver.getCurrentUrl();
        WebElement button = webDriver.findElement(By.cssSelector("a[data-product_id='29']"));
        button.click();
        WebDriverWait wait = new WebDriverWait(webDriver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[title='View cart']")));
        webDriver.findElement(By.cssSelector("a[title='View cart']")).click();
        String redirectedCartUrl = webDriver.getCurrentUrl();

        WebElement product = webDriver.findElement(By.linkText("BDD Cucumber"));

        // then
        Assertions.assertEquals(expectedRedirectedCartUrl, redirectedCartUrl);
        Assertions.assertEquals(expectedRedirectedShopUrl, redirectedShopUrl);
        Assertions.assertEquals("BDD Cucumber", product.getText());
    }
}
