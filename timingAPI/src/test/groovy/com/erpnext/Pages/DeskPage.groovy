package com.erpnext.Pages

import com.codeborne.selenide.Condition
import ru.yandex.qatools.allure.annotations.Step

import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.page
import static com.erpnext.Base.TimingAPI.getPageLoadTime
import static com.erpnext.Base.TimingAPI.getPrePageLoadTime

/**
 * Created by artyom on 4/5/2017.
 */
class DeskPage {
    @Step
    def goToDesk() {
        $(".navbar-home").click()
        $("#icon-grid").shouldBe(Condition.visible)
    }

    @Step
    def logout() {
        $(".toolbar-user-fullname").click()
        $("#toolbar-user>li:last-child").click()
    }

    @Step
    def goToSalesOrder() {
        getPrePageLoadTime()
        $(".case-wrapper[data-link=\"List/Sales Order\"]").click()
        $("div[data-page-route=\"List/Sales Order\"] .frappe-list-area").shouldBe(Condition.visible)
        return page(SalesOrderPage.class)
    }

    @Step
    def goToInvoice() {
        getPrePageLoadTime()
        $(".case-wrapper[data-link=\"List/Sales Invoice\"]").click()
        $("div[data-page-route=\"List/Sales Invoice\"] .frappe-list-area").shouldBe(Condition.visible)
        getPageLoadTime()
        return page(SalesOrderPage.class)
    }

    @Step
    def sellingToDelivery() {
        $(".btn.dropdown-toggle.btn-xs.btn-primary").click()
        getPrePageLoadTime()
        $(".btn-group.open > ul > li:nth-child(1)").click()
        $("div[data-page-route=\"Form/Delivery Note\"] .layout-main-section").shouldBe(Condition.visible)
        getPageLoadTime()
        return page(DeliveryPage.class)
    }

    @Step
    def deliveryToInvoice() {
        $("div[data-page-route=\"Form/Delivery Note\"] .form-inner-toolbar .btn-group:nth-child(1) button").click()
        getPrePageLoadTime()
        $(".btn-group.open > ul > li:nth-child(3)").click()
        $("div[data-page-route=\"Form/Sales Invoice\"] .layout-main-section").shouldBe(Condition.visible)
        getPageLoadTime()
        return page(InvoicePage.class)
    }
}
