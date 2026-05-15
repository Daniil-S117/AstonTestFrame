import io.qameta.allure.*;
import org.example.AllureAttachmentUtil;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import java.util.List;

import static org.example.utils.Constants.EXPECTED_TITLE;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Домашняя работа №9")
@Epic("Домашняя работа №9")
@Feature("Проверка блока «Онлайн пополнение без комиссии» сайта mts.by")
public class Lesson09Test extends BaseTest{

    @Test
    @DisplayName("1. Проверить название указанного блока")
    @Story("1. Проверить название указанного блока;")
    @Severity(SeverityLevel.NORMAL)
    @Description("Тест проверяет совпадение названия блока с ожидаемым результатом")
    public void testPaymentBlockTitle() {
        String blockTitle = mainPage.getBlockTitle();
        Allure.step("Получение названия блока");
        AllureAttachmentUtil.captureScreenshot(mainPage.getDriver(), "Скриншот название указанного блока");
        assertEquals(EXPECTED_TITLE, blockTitle, "Название блока не совпадает");
    }

    @Test
    @DisplayName("2. Проверить наличие логотипов платёжных систем")
    @Story("2. Проверить наличие логотипов платёжных систем;")
    @Severity(SeverityLevel.NORMAL)
    @Description("Тест проверяет наличие логотипов платежных систем")
    public void testPaymentBlockLogo() {
        List<WebElement> logos = mainPage.getAllLogos();
        Allure.step("Поиск всех логотипов");
        AllureAttachmentUtil.captureScreenshot(mainPage.getDriver(), "Скриншот блока с логотипами");
        assertFalse(logos.isEmpty(), "Логотипы платежных систем не найдены");
    }

    @Test
    @DisplayName("3. Проверить работу ссылки «Подробнее о сервисе»")
    @Story("3. Проверить работу ссылки «Подробнее о сервисе»;")
    @Severity(SeverityLevel.NORMAL)
    @Description("Тест проверяет адрес ссылки")
    public void testPaymentBlockLink() {
        mainPage.clickLinkInfo();
        Allure.step("Переход по ссылке «Подробнее о сервисе»");
        String currentUrl = mainPage.getCurrentUrl();
        AllureAttachmentUtil.captureScreenshot(mainPage.getDriver(), "Скриншот страницы");
        assertTrue(currentUrl.contains("help/poryadok-oplaty-i-bezopasnost"), "Ссылка 'Подробнее о сервисе' ведет на неверную страницу");
    }

    @Test
    @DisplayName("4. Заполнение полей и проверка кнопки Продолжить")
    @Story("4. Заполнение полей и проверка кнопки Продолжить;")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Тест проверяет возможность перехода на окно оплаты")
    public void testPaymentForm() {
        mainPageService.verifyIframePaymentData();
        Allure.step("Проверяем только вариант «Услуги связи», номер для теста 297777777");
        AllureAttachmentUtil.captureScreenshot(mainPage.getDriver(), "Скриншот полей блока оплаты");
        assertTrue(mainPage.getPageSource().contains("PaymentWidget"), "Переход на окно оплаты не выполнен");
    }
}


