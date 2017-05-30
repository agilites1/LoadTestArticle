package com.erpnext.Tests

import com.erpnext.Base.BaseClass
import com.erpnext.Base.OnFailure
import com.erpnext.Pages.DeliveryPage
import com.erpnext.Pages.DeskPage
import com.erpnext.Pages.InvoicePage
import com.erpnext.Pages.LoginPage
import com.erpnext.Pages.SalesOrderPage
import org.testng.annotations.Listeners
import org.testng.annotations.Test

import static com.erpnext.Base.TimingAPI.getAllTiming

/**
 * Created by artyom on 3/27/2017.
 */
@Listeners([OnFailure.class])
class TimingTest implements BaseClass {
    @Test
    void UserSideTimingTest() {
        def sellNo, deliveryNo, invoiceNo
        LoginPage loginPage = new LoginPage()
        loginPage.openLoginPage("http://192.168.0.51/login#login")
        DeskPage deskPage = loginPage.login("qw2@er.ty", "123456")
        SalesOrderPage salesOrderPage = deskPage.goToSalesOrder()
        sellNo = salesOrderPage.createSelling()
        DeliveryPage deliveryPage = deskPage.sellingToDelivery()
        deliveryNo = deliveryPage.createDelivery()
        InvoicePage invoicePage = deskPage.deliveryToInvoice()
        invoiceNo = invoicePage.createInvoice()
        //delete
        deskPage.goToDesk()
        deskPage.goToSalesOrder()
        salesOrderPage.openOrder(sellNo)
        salesOrderPage.openInvoiceNote(invoiceNo)
        invoicePage.deleteInvoice(invoiceNo)
        deskPage.goToDesk()
        deskPage.goToSalesOrder()
        salesOrderPage.openOrder(sellNo)
        salesOrderPage.openDeliveryNote(deliveryNo)
        deliveryPage.deleteDelivery(deliveryNo)
        deskPage.goToDesk()
        deskPage.goToSalesOrder()
        salesOrderPage.openOrder(sellNo)
        salesOrderPage.deleteSalesOrder(sellNo)
        deskPage.logout()

    }
    @Test(dependsOnMethods = ["UserSideTimingTest"], alwaysRun = true)
    void allTiming(){
        getAllTiming()
    }
}
