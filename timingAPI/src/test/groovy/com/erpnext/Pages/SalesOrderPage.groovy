package com.erpnext.Pages

import com.codeborne.selenide.Condition
import org.openqa.selenium.By
import ru.yandex.qatools.allure.annotations.Step

import static com.codeborne.selenide.Selectors.by
import static com.codeborne.selenide.Selectors.byText
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$$
import static com.erpnext.Base.TimingAPI.getPageLoadTime
import static com.erpnext.Base.TimingAPI.getPrePageLoadTime

/**
 * Created by artyom on 4/5/2017.
 */
class SalesOrderPage {
    @Step
    def createSelling() {
        makeNewSelling()
        fillSellingForm()
        def sellNo = submitSellingForm()
        return sellNo
    }

    @Step
    def makeNewSelling() {
        $("div[data-page-route=\"List/Sales Order\"] .primary-action").click()
        $("div[data-page-route=\"Form/Sales Order\"] .form-layout").shouldBe(Condition.visible)
    }

    @Step
    def fillSellingForm() {
        $("div[data-page-route=\"Form/Sales Order\"] .editable-title span")
                .shouldHave(Condition.text("Not Saved"))
        $(by("data-fieldname", "customer")).click()
        $(".ui-menu-item-wrapper>p>strong").click()
        $(by("data-fieldname", "delivery_date")).click()
        $(".ui-datepicker-calendar").$(byText(new Date().format('d'))).click()
        //$$("div[data-fieldname=\"items\"] div[data-fieldname=\"item_code\"]").get(1).click()
        $$("div[data-fieldname=\"items\"] div[data-fieldname=\"item_code\"]").get(1).click()
        $$("div[data-fieldname=\"items\"] div[data-fieldname=\"item_code\"]").get(1)
                .$("input").sendKeys("Water")
        $("div[data-fieldname=\"items\"] .grid-add-row").click()
        $$("div[data-fieldname=\"items\"] div[data-fieldname=\"item_code\"]").get(3).click()
        $$("div[data-fieldname=\"items\"] div[data-fieldname=\"item_code\"]").get(3)
                .$("input").sendKeys("Air")
        $(byText("Air")).click()
        getPrePageLoadTime()
        $("div[data-page-route=\"Form/Sales Order\"] .primary-action").click()
        $("div[data-page-route=\"Form/Sales Order\"] .editable-title span")
                .shouldHave(Condition.text("Draft"))
        getPageLoadTime()
    }

    @Step
    def submitSellingForm() {
        $("div[data-page-route=\"Form/Sales Order\"] .primary-action").click()
        getPrePageLoadTime()
        $("div.modal.fade.in .btn-primary.btn-sm").click()
        $("div[data-page-route=\"Form/Sales Order\"] .editable-title span")
                .shouldHave(Condition.text("To Deliver and Bill"))
        getPageLoadTime()
        return $("div[data-page-route=\"Form/Sales Order\"] .page-actions h6").text
    }

    @Step
    def openOrder(String sellNo) {
        $(By.xpath(".//*[@class='result-list']/descendant::a[contains(text(),'" + sellNo + "')]/ancestor-or-self::div[@class='list-row']")).click()
    }

    @Step
    def openDeliveryNote(String deliveryNo) {
        $("div[data-page-route=\"Form/Sales Order\"] div[data-doctype=\"Delivery Note\"] > a").click()
    }

    @Step
    def openInvoiceNote(String invoiceNo) {
        $("div[data-page-route=\"Form/Sales Order\"] div[data-doctype=\"Sales Invoice\"] > a").click()
    }
    @Step
    def deleteSalesOrder(String sellNo) {
        cancelSalesItem(sellNo)
        deleteSalesItem(sellNo)
    }
    @Step
    def cancelSalesItem(String sellNo){
        $("div[data-page-route=\"Form/Sales Order\"] .editable-title span")
                .shouldHave(Condition.text("To Deliver and Bill"))
        $("div[data-page-route=\"Form/Sales Order\"] .btn.btn-secondary").click()
        $("div.modal.fade.in p").shouldHave(Condition.text("Permanently Cancel " + sellNo + "?"))
        $("div.modal.fade.in .btn-primary.btn-sm").click()
        $("div[data-page-route=\"Form/Sales Order\"] .editable-title span")
                .shouldHave(Condition.text("Cancelled"))
    }
    @Step
    def deleteSalesItem(String sellNo){
        $("div[data-page-route=\"Form/Sales Order\"] .menu-btn-group").click()
        $("[data-page-route=\"Form/Sales Order\"] li:nth-child(7)").click()
        $("div.modal.fade.in p").shouldHave(Condition.text("Permanently delete " + sellNo + "?"))
        getPrePageLoadTime()
        $("div.modal.fade.in .btn-primary.btn-sm").click()
        $("div[data-page-route=\"List/Sales Order\"] .frappe-list-area").shouldBe(Condition.visible)
        getPageLoadTime()
    }
}

