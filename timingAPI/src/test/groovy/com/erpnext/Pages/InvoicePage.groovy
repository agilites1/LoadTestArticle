package com.erpnext.Pages

import com.codeborne.selenide.Condition
import org.openqa.selenium.By
import ru.yandex.qatools.allure.annotations.Step

import static com.codeborne.selenide.Selenide.$
import static com.erpnext.Base.TimingAPI.getPageLoadTime
import static com.erpnext.Base.TimingAPI.getPrePageLoadTime

/**
 * Created by artyom on 4/6/2017.
 */
class InvoicePage {
    @Step
    createInvoice() {
        makeNewInvoice()
        def invoiceNo = submitInvoice()
        return invoiceNo
    }

    @Step
    def makeNewInvoice() {
        $("div[data-page-route=\"Form/Sales Invoice\"] .primary-action").click()
        $("div[data-page-route=\"Form/Sales Invoice\"] .editable-title span")
                .shouldHave(Condition.text("Draft"))
    }

    @Step
    def submitInvoice() {
        $("div[data-page-route=\"Form/Sales Invoice\"] .primary-action").click()
        $("div.modal.fade.in .btn-primary.btn-sm").click()
        getPrePageLoadTime()
        $("div.modal.fade.in .btn-modal-close").click()
        $("div[data-page-route=\"Form/Sales Invoice\"] .editable-title span")
                .shouldHave(Condition.text("Unpaid"))
        getPageLoadTime()
        return $("div[data-page-route=\"Form/Sales Invoice\"] .page-actions h6").text
    }

    @Step
    def deleteInvoice(String invoiceNo) {
        cancelInvoiceItem(invoiceNo)
        deleteInvoiceItem(invoiceNo)
    }

    @Step
    def cancelInvoiceItem(String invoiceNo) {
        $(By.xpath(".//*[@class='result-list']/descendant::a[contains(text(),'" + invoiceNo + "')]/ancestor-or-self::div[@class='list-row']")).click()
        $("div[data-page-route=\"Form/Sales Invoice\"] .editable-title span")
                .shouldHave(Condition.text("Unpaid"))
        $("div[data-page-route=\"Form/Sales Invoice\"] .btn.btn-secondary").click()
        $("div.modal.fade.in p").shouldHave(Condition.text("Permanently Cancel " + invoiceNo + "?"))
        $("div.modal.fade.in .btn-primary.btn-sm").click()
        $("div[data-page-route=\"Form/Sales Invoice\"] .editable-title span")
                .shouldHave(Condition.text("Cancelled"))
    }

    @Step
    def deleteInvoiceItem(String invoiceNo) {
        $("div[data-page-route=\"Form/Sales Invoice\"] .menu-btn-group").click()
        $("[data-page-route=\"Form/Sales Invoice\"] li:nth-child(7)").click()
        $("div.modal.fade.in p").shouldHave(Condition.text("Permanently delete " + invoiceNo + "?"))
        getPrePageLoadTime()
        $("div.modal.fade.in .btn-primary.btn-sm").click()
        $("div[data-page-route=\"List/Sales Invoice\"] .frappe-list-area").shouldBe(Condition.visible)
        getPageLoadTime()
    }
}
