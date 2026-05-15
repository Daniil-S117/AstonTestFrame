package org.example.page;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.example.DriveManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

@Epic("Платежная система")
@Feature("Онлайн пополнение без комиссии")
public class MainPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    // Локаторы главной страницы
    final By COOKIE_AGREE = By.id("cookie-agree");
    final By PAY_BLOCK_TITLE = By.xpath("//section[@class='pay']//h2");
    final By SYSTEMS_LOGOS = By.xpath("//div[@class='pay__partners']//li/img");
    final By LINK_INFO = By.linkText("Подробнее о сервисе");
    final By SELECT_BUTTON = By.id("pay");
    final By PHONE_FIELD = By.id("connection-phone");
    final By SUM_FIELD = By.id("connection-sum");
    final By EMAIL_FIELD = By.id("connection-email");
    final By CONTINUE_BUTTON = By.xpath("//form[@id='pay-connection']//button[contains(text(),'Продолжить')]");

    // Локаторы внутри iframe bePaid
    final By IFRAME = By.className("payment-widget-iframe");
    final By PAYMENT_SUM = By.xpath("//div[contains(@class, 'pay-description__cost')]//span");
    final By PAYMENT_BUTTON_SUM = By.xpath("//button[contains(@type, 'submit')]");
    final By PAYMENT_PHONE = By.xpath("//span[contains(text(), '375297777777')]");
    final By CARD_NUMBER_FIELD = By.xpath("//app-input[contains(@class, 'card-number')]//label");
    final By EXPIRATION_DATE_FIELD = By.xpath("//div[contains(@class, 'expires-input')]//label");
    final By CVC_CODE_FIELD = By.xpath("//app-input[contains(@class, 'cvc')]//label");
    final By CARDHOLDER_NAME_FIELD = By.xpath("//div[contains(@class, 'content ng-tns-c2312288139-3')]//label");
    final By CARD_ICONS = By.xpath("//div[contains(@class, 'cards-brands')]//img");

    public MainPage(){
        this.driver = DriveManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPage(String url){
        driver.get(url);
    }

    public void clickCookie(){
        driver.findElement(COOKIE_AGREE).click();
    }

    public String getBlockTitle() {
        return driver.findElement(PAY_BLOCK_TITLE).getText();
    }

    public List<WebElement> getAllLogos() {
        return driver.findElements(SYSTEMS_LOGOS);
    }

    public void clickLinkInfo() {
        driver.findElement(LINK_INFO).click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    @Step("Выбор опции оплаты: {optionName}")
    public MainPage selectPaymentOption(String optionName) {
        WebElement selectElement = driver.findElement(SELECT_BUTTON);
        Select select = new Select(selectElement);
        select.selectByVisibleText(optionName);
        return this;
    }

    public String getPlaceholder(String fieldId) {
        return driver.findElement(By.id(fieldId)).getAttribute("placeholder");
    }

    @Step("Заполнение формы: телефон {phone}, сумма {sum}, email {email}")
    public MainPage fillConnectionForm(String phone, String sum, String email) {
        driver.findElement(PHONE_FIELD).sendKeys(phone);
        driver.findElement(SUM_FIELD).sendKeys(sum);
        driver.findElement(EMAIL_FIELD).sendKeys(email);
        return this;
    }

    @Step("Нажатие кнопки «Продолжить»")
    public MainPage clickContinue() {
        driver.findElement(CONTINUE_BUTTON).click();
        return this;
    }

    @Step("Переключение во фрейм оплаты")
    public void switchToPaymentIframe() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IFRAME));
        wait.until(ExpectedConditions.visibilityOfElementLocated(PAYMENT_SUM));
    }

    public String getIframeSum() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PAYMENT_SUM)).getText();
    }

    public String getIframeButtonText() {
        return driver.findElement(PAYMENT_BUTTON_SUM).getText();
    }

    public String phoneDisplayed() {
        return driver.findElement(PAYMENT_PHONE).getText();
    }

    public String getIframePlaceholderCardNumber() {
        return driver.findElement(CARD_NUMBER_FIELD).getText();
    }

    public String getIframePlaceholderExpirationDate() {
        return driver.findElement(EXPIRATION_DATE_FIELD).getText();
    }

    public String getIframePlaceholderCVC() {
        return driver.findElement(CVC_CODE_FIELD).getText();
    }

    public String getIframePlaceholderCardholderName() {
        return driver.findElement(CARDHOLDER_NAME_FIELD).getText();
    }

    public int getCardIconsCount() {
        return driver.findElements(CARD_ICONS).size();
    }

    public WebDriver getDriver(){
        return driver;
    }
}
