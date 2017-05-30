package com.erpnext.Pages

import com.codeborne.selenide.Condition
import org.openqa.selenium.By
import ru.yandex.qatools.allure.annotations.Step

import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$$
import static com.erpnext.Base.TimingAPI.getPageLoadTime
import static com.erpnext.Base.TimingAPI.getPrePageLoadTime

/**
 * Created by artyom on 4/6/2017.
 */
class DeliveryPage {
    def createDelivery() {
        makeNewDelivery()
        def deliveryNo = submitDelivery()
        return deliveryNo
    }

    @Step
    def makeNewDelivery() {
        $("div[data-page-route=\"Form/Delivery Note\"] .primary-action").click()
        $("div[data-page-route=\"Form/Delivery Note\"] .editable-title span")
                .shouldHave(Condition.text("Draft"))
    }

    @Step
    def submitDelivery() {
        $("div[data-page-route=\"Form/Delivery Note\"] .primary-action").click()
        getPrePageLoadTime()
        $("div.modal.fade.in .btn-primary.btn-sm").click()
        $$(".editable-title span").last().shouldHave(Condition.text("To Bill"))
        getPageLoadTime()
        $("div[data-page-route=\"Form/Delivery Note\"] .editable-title span")
                .shouldHave(Condition.text("To Bill"))
        return $("div[data-page-route=\"Form/Delivery Note\"] .page-actions h6").text
    }

    @Step
    def deleteDelivery(String deliveryNo) {
        cancelDeliveryItem(deliveryNo)
        deleteDeliveryItem(deliveryNo)
    }

    @Step
    def cancelDeliveryItem(String deliveryNo) {
        $(By.xpath(".//*[@class='result-list']/descendant::a[contains(text(),'" + deliveryNo + "')]/ancestor-or-self::div[@class='list-row']")).click()
        $("div[data-page-route=\"Form/Delivery Note\"] .editable-title span")
                .shouldHave(Condition.text("To Bill"))
        $("div[data-page-route=\"Form/Delivery Note\"] .btn.btn-secondary").click()
        $("div.modal.fade.in p").shouldHave(Condition.text("Permanently Cancel " + deliveryNo + "?"))
        $("div.modal.fade.in .btn-primary.btn-sm").click()
        $("div[data-page-route=\"Form/Delivery Note\"] .editable-title span")
                .shouldHave(Condition.text("Cancelled"))
    }

    @Step
    def deleteDeliveryItem(String deliveryNo) {
        $("div[data-page-route=\"Form/Delivery Note\"] .menu-btn-group").click()
        $("[data-page-route=\"Form/Delivery Note\"] li:nth-child(7)").click()
        $("div.modal.fade.in p").shouldHave(Condition.text("Permanently delete " + deliveryNo + "?"))
        getPrePageLoadTime()
        $("div.modal.fade.in .btn-primary.btn-sm").click()
        $("div[data-page-route=\"List/Delivery Note\"] .frappe-list-area").shouldBe(Condition.visible)
        getPageLoadTime()
    }
}
