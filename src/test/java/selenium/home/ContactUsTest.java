package selenium.home;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.BaseTest;

import java.util.List;
import java.util.stream.IntStream;

public class ContactUsTest extends BaseTest {

    private static final String DATA = "Test data";
    private static final String MAIL = "testmail@test.test";
    private static final String SUCCESSFUL_MESSAGE = "It is demo page! We are not sending emails!";
    private static final String UNSUCCESSFUL_MESSAGE = "Invalid form submission : some fields have not been entered properly.";


    @Test
    public void contactSuccessfully() {
        // given
        webDriver.get(SELENIUM_BASE_URL);

        // when
        fillInputs(List.of(DATA, MAIL, DATA));

        webDriver.findElement(By.id("nimble_submit940016385")).click();
        WebElement result = webDriver.findElement(By.className("sek-form-message"));

        // then
        Assertions.assertEquals(SUCCESSFUL_MESSAGE, result.getText());
    }


    @Test
    public void wrongEmail() {
        // given
        webDriver.get(SELENIUM_BASE_URL);

        // when
        fillInputs(List.of(DATA, DATA, DATA));

        webDriver.findElement(By.id("nimble_submit940016385")).click();
        WebElement result = webDriver.findElement(By.className("sek-form-message"));

        // then
        Assertions.assertTrue(result.getText().contains(UNSUCCESSFUL_MESSAGE));
    }

    private void fillInputs(List<String> values) {
        List<WebElement> inputs = getInputs();
        IntStream.range(0, inputs.size())
                .forEach(i -> {
                    inputs.get(i).sendKeys(values.get(i));
                });
    }

    private List<WebElement> getInputs() {
        return List.of(
                webDriver.findElement(By.id("nimble_name940016385")),
                webDriver.findElement(By.id("nimble_email940016385")),
                webDriver.findElement(By.id("nimble_message940016385"))
        );
    }
}
