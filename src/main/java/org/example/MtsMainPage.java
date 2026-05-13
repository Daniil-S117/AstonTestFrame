package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MtsMainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы главной страницы
    private final By payBlockTitle = By.xpath("//section[@class='pay']//h2");
    private final By systemsLogos = By.xpath("//div[@class='pay__partners']//li/img");
    private final By linkInfo = By.linkText("Подробнее о сервисе");
    private final By selectButton = By.id("pay");
    private final By phoneField = By.id("connection-phone");
    private final By sumField = By.id("connection-sum");
    private final By emailField = By.id("connection-email");
    private final By continueButton = By.xpath("//form[@id='pay-connection']//button[contains(text(),'Продолжить')]");

    // Локаторы внутри iframe bePaid
    private final By iframe = By.className("payment-widget-iframe");
    private final By paymentSum = By.xpath("//div[contains(@class, 'pay-description__cost')]//span");
    private final By paymentButtonSum = By.xpath("//button[contains(@type, 'submit')]");
    private final By paymentPhone = By.xpath("//span[contains(text(), '375297777777')]");
    private final By cardNumberField = By.xpath("//app-input[contains(@class, 'card-number')]//label");
    private final By expirationDateField = By.xpath("//div[contains(@class, 'expires-input')]//label");
    private final By cvcCodeField = By.xpath("//app-input[contains(@class, 'cvc')]//label");
    private final By cardholderNameField = By.xpath("//div[contains(@class, 'content ng-tns-c2312288139-3')]//label");
    private final By cardIcons = By.xpath("//div[contains(@class, 'cards-brands')]//img");

    public MtsMainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickCookie(){
        driver.findElement(By.id("cookie-agree")).click();
    }

    public String getBlockTitle() {
        return driver.findElement(payBlockTitle).getText();
    }

    public List<WebElement> getAllLogos() {
        return driver.findElements(systemsLogos);
    }

    public void clickLinkInfo() {
        driver.findElement(linkInfo).click();
    }

    public void selectPaymentOption(String optionName) {
        WebElement selectElement = driver.findElement(selectButton);
        Select select = new Select(selectElement);
        select.selectByVisibleText(optionName);
    }

    public String getPlaceholder(String fieldId) {
        return driver.findElement(By.id(fieldId)).getAttribute("placeholder");
    }

    public void fillConnectionForm(String phone, String sum, String email) {
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(sumField).sendKeys(sum);
        driver.findElement(emailField).sendKeys(email);
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    public void switchToPaymentIframe() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));
        wait.until(ExpectedConditions.visibilityOfElementLocated(paymentSum));
    }

    public String getIframeSum() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(paymentSum)).getText();
    }

    public String getIframeButtonText() {
        return driver.findElement(paymentButtonSum).getText();
    }

    public String phoneDisplayed() {
        return driver.findElement(paymentPhone).getText();
    }

    public String getIframePlaceholderCardNumber() {
        return driver.findElement(cardNumberField).getText();
    }

    public String getIframePlaceholderExpirationDate() {
        return driver.findElement(expirationDateField).getText();
    }

    public String getIframePlaceholderCVC() {
        return driver.findElement(cvcCodeField).getText();
    }

    public String getIframePlaceholderCardholderName() {
        return driver.findElement(cardholderNameField).getText();
    }

    public int getCardIconsCount() {
        return driver.findElements(cardIcons).size();
    }
}
