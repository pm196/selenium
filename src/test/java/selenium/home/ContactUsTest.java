package selenium.home;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import selenium.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ContactUsTest extends BaseTest {

    private static final String DATA = "Test data";
    private static final String MAIL = "testmail@test.test";
    private static final String BLANK = "";
    private static final String SUCCESSFUL_MESSAGE = "It is demo page! We are not sending emails!";
    private static final String UNSUCCESSFUL_MESSAGE = "Invalid form submission : some fields have not been entered properly.";
    private static final By SUBMIT_BUTTON = By.id("nimble_submit1786504237");
    public static final By INPUT_NAME = By.name("nimble_name");
    public static final By INPUT_EMAIL = By.name("nimble_email");
    public static final By INPUT_MESSAGE = By.name("nimble_message");
    public static final By MESSAGE_AFTER_SUBMIT = By.className("sek-form-message");

    @Test
    public void contactSuccessfully() {
        // given
        webDriver.get(SELENIUM_BASE_URL);

        // when
        fillInputs(List.of(DATA, MAIL, DATA));

        webDriver.findElement(SUBMIT_BUTTON).click();
        WebElement result = webDriver.findElement(MESSAGE_AFTER_SUBMIT);

        // then
        Assertions.assertEquals(SUCCESSFUL_MESSAGE, result.getText());
    }


    @Test
    public void wrongEmail() {
        // given
        webDriver.get(SELENIUM_BASE_URL);

        // when
        fillInputs(List.of(DATA, DATA, DATA));

        webDriver.findElement(SUBMIT_BUTTON).click();
        WebElement result = webDriver.findElement(MESSAGE_AFTER_SUBMIT);

        // then
        Assertions.assertTrue(result.getText().contains(UNSUCCESSFUL_MESSAGE));
    }

    @ParameterizedTest
    @MethodSource("data")
    public void oneBlankField(List<String> data) {
        // given
        webDriver.get(SELENIUM_BASE_URL);

        // when
        fillInputs(data);

        webDriver.findElement(SUBMIT_BUTTON).click();
        boolean result;
        try{
            webDriver.findElement(MESSAGE_AFTER_SUBMIT);
            result = false;
        } catch (NoSuchElementException e){
            result = true;
        }

        // then
        Assertions.assertTrue(result);
    }


    static Stream<Arguments> data() {
        return Stream.of(Arguments.of(List.of(DATA, DATA, BLANK), List.of(DATA, BLANK, DATA), List.of(DATA, DATA, BLANK)));
    }

    private void fillInputs(List<String> values) {
        List<WebElement> inputs = getInputs();
        IntStream.range(0, inputs.size())
                .forEach(i -> inputs.get(i).sendKeys(values.get(i)));
    }


    private List<WebElement> getInputs() {
        return List.of(
                webDriver.findElement(INPUT_NAME),
                webDriver.findElement(INPUT_EMAIL),
                webDriver.findElement(INPUT_MESSAGE)
        );
    }
}
