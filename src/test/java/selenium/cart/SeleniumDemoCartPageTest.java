package selenium.cart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.BaseTest;

import java.time.Duration;
import java.util.Arrays;

public class SeleniumDemoCartPageTest extends BaseTest {
    public static final By CART = By.xpath("//span[text()='Cart']");
    public static final By SHOP = By.xpath("//span[text()='Shop']");
    public static final By VIEW_CART = By.cssSelector("a[title='View cart']");
    public static final By CART_EMPTY = By.className("cart-empty");
    public static final By BDD_CUCUMBER = By.linkText("BDD Cucumber");
    public static final By CUCUMBER = By.partialLinkText("Cucumber");
    public static final By GIT = By.partialLinkText("GIT");
    public static final By JAVA = By.partialLinkText("Java");
    public static final By TOTAL_PRICE_AMOUNT = By.cssSelector("tr.order-total span.woocommerce-Price-amount");

    @Test
    public void testEmptyCart() {
        // given
        webDriver.get(SELENIUM_BASE_URL);

        // when
        WebElement cartWebElement = webDriver.findElement(CART);
        cartWebElement.click();
        String redirectedUrl = webDriver.getCurrentUrl();
        WebElement emptyCart = webDriver.findElement(CART_EMPTY);

        // then
        Assertions.assertEquals(EXPECTED_REDIRECTED_CART_URL, redirectedUrl);
        Assertions.assertTrue(emptyCart.isDisplayed());

    }

    @Test
    public void testAddingToCartOneElement() {
        // given
        webDriver.get(SELENIUM_BASE_URL);

        // when
        WebElement cartWebElement = webDriver.findElement(SHOP);
        cartWebElement.click();
        String redirectedShopUrl = webDriver.getCurrentUrl();
        clickAddToCart(29);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(VIEW_CART));
        webDriver.findElement(VIEW_CART).click();
        String redirectedCartUrl = webDriver.getCurrentUrl();

        WebElement product = webDriver.findElement(BDD_CUCUMBER);

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
        WebElement cartWebElement = webDriver.findElement(SHOP);
        cartWebElement.click();
        String redirectedShopUrl = webDriver.getCurrentUrl();

        Arrays.asList(29, 27, 8).forEach(this::clickAddToCart);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(CART));
        webDriver.findElement(CART).click();
        String redirectedCartUrl = webDriver.getCurrentUrl();

        WebElement bdd_cucumber = webDriver.findElement(CUCUMBER);
        WebElement git_basics = webDriver.findElement(GIT);
        WebElement java_selenium = webDriver.findElement(JAVA);
        WebElement price = webDriver.findElement(TOTAL_PRICE_AMOUNT);

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
