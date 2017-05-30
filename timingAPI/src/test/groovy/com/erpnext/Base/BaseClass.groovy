package com.erpnext.Base

import com.codeborne.selenide.WebDriverRunner
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass

/**
 * Created by artyom on 3/27/2017.
 */
trait BaseClass {
    WebDriver driver

    @BeforeClass
    def setUp() {
        def capabilities
        capabilities = (System.properties['selenide.browser'] == 'chrome'
                ? DesiredCapabilities.chrome() : DesiredCapabilities.firefox())
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities)
        WebDriverRunner.setWebDriver(driver)
    }

    static String normalizeText(String text) {
        text.replaceAll(/([A-Z])/, / $1/).replaceAll(/^_/, '').capitalize()
    }

    @AfterClass
    def tearDown() {
        driver.quit()
    }
}