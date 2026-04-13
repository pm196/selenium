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

import java.util.Arrays;

public class SeleniumDemoCartPageTest {

    private WebDriver webDriver;
    private final String SELENIUM_BASE_URL = "http://seleniumdemo.com/";
    public static final String EXPECTED_REDIRECTED_SHOP_URL = "http://seleniumdemo.com/?post_type=product";
    public static final String EXPECTED_REDIRECTED_CART_URL = "http://seleniumdemo.com/?page_id=5";


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

        // when
        WebElement cartWebElement = webDriver.findElement(By.xpath("//span[text()='Shop']"));
        cartWebElement.click();
        String redirectedShopUrl = webDriver.getCurrentUrl();
        clickAddToCart(29);
        WebDriverWait wait = new WebDriverWait(webDriver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[title='View cart']")));
        webDriver.findElement(By.cssSelector("a[title='View cart']")).click();
        String redirectedCartUrl = webDriver.getCurrentUrl();

        WebElement product = webDriver.findElement(By.linkText("BDD Cucumber"));

        // then
        Assertions.assertEquals(EXPECTED_REDIRECTED_CART_URL, redirectedCartUrl);
        Assertions.assertEquals(EXPECTED_REDIRECTED_SHOP_URL, redirectedShopUrl);
        Assertions.assertEquals("BDD Cucumber", product.getText());
    }

    @Test
    public void testAddingToCartAllElements() {
        // given
        webDriver.get(SELENIUM_BASE_URL);

        // when
        WebElement cartWebElement = webDriver.findElement(By.xpath("//span[text()='Shop']"));
        cartWebElement.click();
        String redirectedShopUrl = webDriver.getCurrentUrl();

        Arrays.asList(29, 27, 8).forEach(this::clickAddToCart);
        WebDriverWait wait = new WebDriverWait(webDriver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[title='View cart']")));
        webDriver.findElement(By.cssSelector("a[title='View cart']")).click();
        String redirectedCartUrl = webDriver.getCurrentUrl();

        WebElement bdd_cucumber = webDriver.findElement(By.partialLinkText("Cucumber"));
        WebElement git_basics = webDriver.findElement(By.partialLinkText("GIT"));
        WebElement java_selenium = webDriver.findElement(By.partialLinkText("Java"));
        WebElement price = webDriver.findElement(By.cssSelector("tr.order-total span.woocommerce-Price-amount"));

        // then
        Assertions.assertEquals(EXPECTED_REDIRECTED_CART_URL, redirectedCartUrl);
        Assertions.assertEquals(EXPECTED_REDIRECTED_SHOP_URL, redirectedShopUrl);
        Assertions.assertEquals("BDD Cucumber", bdd_cucumber.getText());
        Assertions.assertEquals("GIT basics", git_basics.getText());
        Assertions.assertEquals("Java Selenium WebDriver", java_selenium.getText());
        Assertions.assertEquals("11,99 zł", price.getText());
    }

    private void clickAddToCart(int id) {
        webDriver.findElement(By.cssSelector("a[data-product_id='" + id + "']")).click();
    }
}
