package org.example.service;
import io.qameta.allure.Step;
import org.example.page.MainPage;
import static org.example.utils.Constants.*;


public class MainPageService {
    private final MainPage mainPage = new MainPage();

    @Step("Заполнение полей в соответствии с пререквизитами из Занятия 9, нажатие кнопки «Продолжить»")
    public void verifyIframePaymentData() {
        mainPage.selectPaymentOption(PAYMENT_OPTION)
                .fillConnectionForm(PHONE_NUMBER, SUM, EMAIL)
                .clickContinue()
                .switchToPaymentIframe();
    }
}
