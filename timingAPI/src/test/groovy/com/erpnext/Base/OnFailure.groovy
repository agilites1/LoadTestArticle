package com.erpnext.Base

import com.codeborne.selenide.Screenshots
import com.google.common.io.Files
import org.testng.ITestResult
import org.testng.TestListenerAdapter
import ru.yandex.qatools.allure.annotations.Attachment

/**
 * Created by artyom on 3/27/2017.
 */
class OnFailure extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult tr) {
        try {
            screenshot()
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    @Attachment(type = "image/png")
    public byte[] screenshot() throws IOException {
        File screenshot = Screenshots.getLastScreenshot()
        return Files.toByteArray(screenshot)
    }
}