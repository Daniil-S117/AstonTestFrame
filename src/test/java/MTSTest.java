import org.example.DriveManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MTSTest {

    protected WebDriver driver = DriveManager.getDriver();
    protected WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @BeforeEach
    public void setUp() {
        driver.get("https://www.mts.by");

        // Закрываем куки, если они перекрывают элементы
        try {
            driver.findElement(By.id("cookie-agree")).click();
        } catch (Exception ignored) {
        }
    }

    // Проверка блока Онлайн пополнение без комиссии
    @Test
    @DisplayName("1. Проверить название указанного блока")
    public void testOnlinePaymentBlockTitle() {
        WebElement blockTitle = driver.findElement(By.xpath("//section[@class='pay']//h2"));
        String expectedTitle = "Онлайн пополнение\nбез комиссии";
        assertEquals(expectedTitle, blockTitle.getText(), "Название блока не совпадает");
        System.out.println(blockTitle.getText());
    }

    @Test
    @DisplayName("2. Проверить наличие логотипов платёжных систем")
    public void testOnlinePaymentBlockLogo() {
        List<WebElement> logos = driver.findElements(By.xpath("//div[@class='pay__partners']//li/img"));
        assertFalse(logos.isEmpty(), "Логотипы платежных систем не найдены");
        System.out.println("Найдено логотипов: " + logos.size());
    }

    @Test
    @DisplayName("3. Проверить работу ссылки «Подробнее о сервисе»")
    public void testOnlinePaymentBlockLink() {
        driver.findElement(By.linkText("Подробнее о сервисе")).click();
        assertTrue(driver.getCurrentUrl().contains("help/poryadok-oplaty-i-bezopasnost"), "Ссылка 'Подробнее о сервисе' ведет на неверную страницу");
        System.out.println(driver.getCurrentUrl());
    }

    @Test
    @DisplayName("4. Заполнение полей и проверка кнопки Продолжить")
    public void testPaymentForm() {
        // Убеждаемся, что выбран вариант «Услуги связи» (обычно выбран по умолчанию)
        // 1) Находим кнопку выпадающего списка
        WebElement selectButton = driver.findElement(By.xpath("//div[@class='select']//button"));

        // 2) Проверяем, какой текст сейчас отображается на кнопке
        String currentOption = selectButton.getText();
        System.out.println("Сейчас выбрано: " + currentOption);

        // 3) Если выбрано не "Услуги связи", кликаем и выбираем нужный пункт
        if (!currentOption.contains("Услуги связи")) {
            selectButton.click(); // Открываем список

            // Ждем появления выпадающего меню и кликаем по пункту "Услуги связи"
            WebElement optionServices = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//ul[@class='select__list']//span[text()='Услуги связи']")
            ));
            optionServices.click();
        }

        // 4) Заполняем номер телефона (номер из задания: 297777777)
        driver.findElement(By.id("connection-phone")).sendKeys("297777777");

        // 5) Заполняем сумму (например, 100 руб)
        driver.findElement(By.id("connection-sum")).sendKeys("100");

        // 6) Заполняем e-mail
        driver.findElement(By.id("connection-email")).sendKeys("test@test.by");

        // 7) Нажимаем кнопку «Продолжить»
        driver.findElement(By.xpath("//button[contains(text(),'Продолжить')]")).click();

        // 8) Ожидание появления iframe и переключение в него
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.className("payment-widget-iframe")));
        System.out.println(driver.getPageSource());
        // 9) Проверяет, что появилась форма оплаты
        assertTrue(driver.getPageSource().contains("PaymentWidget"), "Переход на окно оплаты не выполнен");
    }

    @AfterAll
    public static void tearDown(){
        DriveManager.quitDriver();
    }
}

