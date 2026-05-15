import io.qameta.allure.*;
import org.example.AllureAttachmentUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Домашняя работа №10")
@Epic("Домашняя работа №10")
@Feature("Продолжим работу над блоком «Онлайн пополнение без комиссии» сайта mts.by.")
public class Lesson10Test extends BaseTest{

    @ParameterizedTest
    @CsvFileSource(resources = "/source.csv")
    @DisplayName("1. Проверить надписи в незаполненных полях каждого варианта оплаты услуг")
    @Story("1. Проверить надписи в незаполненных полях каждого варианта оплаты услуг;")
    @Severity(SeverityLevel.MINOR)
    @Description("Проверить все поля вариантов услуги связи, домашний интернет, рассрочка, задолженность")
    public void testPlaceholders(String option, String placeholder, String sum, String email) {
        mainPage.selectPaymentOption(option);
        String numberPlaceholder = mainPage.getPlaceholder(placeholder);
        String sumPlaceholder = mainPage.getPlaceholder(sum);
        String emailPlaceholder = mainPage.getPlaceholder(email);
        Allure.step("Получение надписей в незаполненных полях");
        AllureAttachmentUtil.captureScreenshot(mainPage.getDriver(), "Скриншот полей с Плейсхолдерами");
        // Утверждения (Assertions) проверки полей, плейсхолдеры могут отличаться
        assertAll("Проверка данных в форме 'Онлайн пополнение без комиссии'",
                () -> assertFalse(numberPlaceholder.isEmpty(),
                        "Плейсхолдер пуст для опции: " + option + " - " + placeholder),
                () -> assertFalse(sumPlaceholder.isEmpty(),
                        "Плейсхолдер пуст для опции: " + option + " - " + sum),
                () -> assertFalse(emailPlaceholder.isEmpty(),
                        "Плейсхолдер пуст для опции: " + option + " - " + email)
        );
    }

    @Test
    @DisplayName("2. Заполнение полей и проверка корректность работы формы оплаты Услуги связи")
    @Story("2. Заполнение полей и проверка корректность работы формы оплаты Услуги связи")
    @Description("Тест проверяет передачу суммы и номера телефона во всплывающее окно оплаты Услуги связи")
    @Severity(SeverityLevel.BLOCKER)
    public void testIframePaymentData() {
        mainPageService.verifyIframePaymentData();
        // 1) Проверка корректности отображения суммы
        String sum = mainPage.getIframeSum();
        Allure.step("Получение суммы в чеке");
        AllureAttachmentUtil.captureScreenshot(mainPage.getDriver(), "Скриншот полей с данными");
        assertTrue(sum.contains(SUM), "Сумма в чеке неверная: " + sum);
        // 2) Проверка суммы на кнопке
        String btnText = mainPage.getIframeButtonText();
        Allure.step("Получение суммы на кнопке");
        assertTrue(btnText.contains(SUM), "Сумма на кнопке неверная: " + btnText);
        // 3) Проверка номера
        String phone = mainPage.phoneDisplayed();
        Allure.step("Получение номера телефона");
        assertTrue(phone.contains(PHONE_NUMBER), "Номер телефона 375297777777 не отображен");
        // 4) Проверка плейсхолдеров карты (внутри iframe)
        assertAll("Проверка плейсхолдеров в окне bePaid",
                () -> assertEquals("Номер карты", mainPage.getIframePlaceholderCardNumber(),
                        "Плейсхолдер для опции отсутствует."),
                () -> assertEquals("Срок действия", mainPage.getIframePlaceholderExpirationDate(),
                        "Плейсхолдер для опции отсутствует."),
                () -> assertEquals("CVC", mainPage.getIframePlaceholderCVC(),
                        "Плейсхолдер для опции отсутствует."),
                () -> assertEquals("Имя и фамилия на карте", mainPage.getIframePlaceholderCardholderName(),
                        "Плейсхолдер для опции отсутствует.")
        );
        Allure.step("Получение надписей в незаполненных полях для ввода реквизитов карты");
        AllureAttachmentUtil.captureScreenshot(mainPage.getDriver(), "Скриншот полей с Плейсхолдерами");
        // 5) Проверка иконок (Visa, MasterCard, Belkart и т.д.)
        int cardIconsCount = mainPage.getCardIconsCount();
        Allure.step("Полученное количество иконок: " + cardIconsCount);
        assertTrue(cardIconsCount > 0, "Иконки платежных систем не найдены");
    }
}
