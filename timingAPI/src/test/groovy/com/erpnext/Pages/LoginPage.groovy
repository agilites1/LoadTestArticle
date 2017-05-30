package com.erpnext.Pages

import com.codeborne.selenide.Condition
import ru.yandex.qatools.allure.annotations.Step

import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.page
import static com.codeborne.selenide.WebDriverRunner.getWebDriver
import static com.erpnext.Base.TimingAPI.getPageLoadTime
import static com.erpnext.Base.TimingAPI.getPrePageLoadTime

/**
 * Created by artyom on 4/5/2017.
 */
class LoginPage {
    @Step
    def openLoginPage(String url){
        getWebDriver().get(url)
        getPageLoadTime()
    }

    @Step
    def login(def username, def password){
        $("#login_email").setValue(username)
        $("#login_password").setValue(password)
        getPrePageLoadTime()
        $(".btn-login").click()
        $("#icon-grid").shouldBe(Condition.visible)
        getPageLoadTime()
        return page(DeskPage.class)
    }
}
