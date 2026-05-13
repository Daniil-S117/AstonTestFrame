import org.example.DriveManager;
import org.example.MtsMainPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.logging.Logger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MtsPaymentTest {
    protected static WebDriver driver = DriveManager.getDriver();
    protected static MtsMainPage mainPage = new MtsMainPage(driver);
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @BeforeEach
    public void setUp() {
        driver.get("https://www.mts.by");
        // Закрываем куки, если они перекрывают элементы
        try {
            driver.findElement(By.id("cookie-agree")).click();
        } catch (Exception ignored) {
        }
    }

    @Test
    @DisplayName("9-1. Проверить название указанного блока")
    public void testPaymentBlockTitle() {
        String blockTitle = mainPage.getBlockTitle();
        String expectedTitle = "Онлайн пополнение\nбез комиссии";
        assertEquals(expectedTitle, blockTitle, "Название блока не совпадает");
        logger.info(blockTitle);
    }

    @Test
    @DisplayName("9-2. Проверить наличие логотипов платёжных систем")
    public void testPaymentBlockLogo() {
        List<WebElement> logos = mainPage.getAllLogos();
        assertFalse(logos.isEmpty(), "Логотипы платежных систем не найдены");
        logger.info("Найдено логотипов: " + logos.size());
    }

    @Test
    @DisplayName("9-3. Проверить работу ссылки «Подробнее о сервисе»")
    public void testPaymentBlockLink() {
        mainPage.clickLinkInfo();
        assertTrue(driver.getCurrentUrl().contains("help/poryadok-oplaty-i-bezopasnost"), "Ссылка 'Подробнее о сервисе' ведет на неверную страницу");
        logger.info(driver.getCurrentUrl());
    }

    @Test
    @DisplayName("9-4. Заполнение полей и проверка кнопки Продолжить")
    public void testPaymentForm() {
        // 1) Если выбрано не "Услуги связи", кликаем и выбираем нужный пункт
        mainPage.selectPaymentOption("Услуги связи");
        // 2) Заполнение номера телефона, суммы, email
        mainPage.fillConnectionForm("297777777", "123", "test@test.com");
        // 3) Нажатие кнопки «Продолжить»
        mainPage.clickContinue();
        // 4) Ожидание появления iframe и переключение в него
        mainPage.switchToPaymentIframe();
        // 5) Проверка, что появилась форма оплаты
        assertTrue(driver.getPageSource().contains("PaymentWidget"), "Переход на окно оплаты не выполнен");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/source.csv")
    @DisplayName("10-1. Проверить надписи в незаполненных полях каждого варианта оплаты услуг")
    public void testPlaceholders(String option, String placeholder, String sum, String email) {
        mainPage.selectPaymentOption(option);
        // Утверждения (Assertions) проверки полей, плейсхолдеры могут отличаться
        assertAll("Проверка данных в форме 'Онлайн пополнение без комиссии'",
                () -> assertFalse(mainPage.getPlaceholder(placeholder).isEmpty(),
                        "Плейсхолдер пуст для опции: " + option + " - " + placeholder),
                () -> assertFalse(mainPage.getPlaceholder(sum).isEmpty(),
                        "Плейсхолдер пуст для опции: " + option + " - " + sum),
                () -> assertFalse(mainPage.getPlaceholder(email).isEmpty(),
                        "Плейсхолдер пуст для опции: " + option + " - " + email)
        );
        logger.info(mainPage.getPlaceholder(placeholder) + " \n"
                + mainPage.getPlaceholder(sum) + " \n"
                + mainPage.getPlaceholder(email));
    }

    @Test
    @DisplayName("10-2. Заполнение полей и проверка корректность работы формы оплаты")
    public void testIframePaymentData() {
        // 1) Выбор оплаты "Услуги связи"
        mainPage.selectPaymentOption("Услуги связи");
        // 2) Заполнение номера телефона, суммы, email
        mainPage.fillConnectionForm("297777777", "123", "test@test.com");
        // 3) Нажатие кнопки «Продолжить»
        mainPage.clickContinue();
        // 4) Ожидание появления iframe и переключение в него
        mainPage.switchToPaymentIframe();
        // 5) Проверка корректности отображения суммы
        String sum = mainPage.getIframeSum();
        logger.info(sum);
        assertTrue(sum.contains("123.00"), "Сумма в чеке неверная: " + sum);
        // 6) Проверка суммы на кнопке
        String btnText = mainPage.getIframeButtonText();
        logger.info(btnText);
        assertTrue(btnText.contains("123.00"), "Сумма на кнопке неверная: " + btnText);
        // 7) Проверка номера
        assertTrue(mainPage.phoneDisplayed().contains("375297777777"), "Номер телефона 375297777777 не отображен");
        logger.info(mainPage.phoneDisplayed());
        // 8) Проверка плейсхолдеров карты (внутри iframe)
        assertAll("Проверка данных в окне bePaid",
                () -> assertEquals("Номер карты", mainPage.getIframePlaceholderCardNumber(),
                        "Плейсхолдер для опции отсутствует."),
                () -> assertEquals("Срок действия", mainPage.getIframePlaceholderExpirationDate(),
                        "Плейсхолдер для опции отсутствует."),
                () -> assertEquals("CVC", mainPage.getIframePlaceholderCVC(),
                        "Плейсхолдер для опции отсутствует."),
                () -> assertEquals("Имя и фамилия на карте", mainPage.getIframePlaceholderCardholderName(),
                        "Плейсхолдер для опции отсутствует.")
        );
        // 9) Проверка иконок (Visa, MasterCard, Belkart и т.д.)
        assertTrue(mainPage.getCardIconsCount() > 0, "Иконки платежных систем не найдены");
        logger.info("Количество иконок: " + mainPage.getCardIconsCount());
    }

    @AfterAll
    public static void tearDown() {
        DriveManager.quitDriver();
    }
}


