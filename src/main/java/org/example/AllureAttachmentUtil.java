package org.example;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.ByteArrayInputStream;

public class AllureAttachmentUtil {
    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));
    }
}   