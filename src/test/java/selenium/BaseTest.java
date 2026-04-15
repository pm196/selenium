package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    protected WebDriver webDriver;
    public static final String SELENIUM_BASE_URL = "http://seleniumdemo.com/";
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
}
