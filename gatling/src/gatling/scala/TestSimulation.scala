
import Models.Delivery.DeliveryOrderModel
import Models.Invoice.{InvoiceSubmitModel, PaymentModel}
import Models.Selling.{SellingItemModel, SellingItems, SellingSubmitModel}
import Models._
import com.google.gson.Gson
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.collection.JavaConversions._

/**
  * Created by artyom on 3/11/2017.
  */


object TestSimulation extends Simulation {
  val gson = new Gson

  val httpProtocol: HttpProtocolBuilder = http
    .baseURL("http://192.168.0.51")
    .inferHtmlResources()
    .acceptHeader("application/json, text/javascript, */*; q=0.01")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0")

  val headers_0 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_2 = Map(
    "Content-Type" -> "application/x-www-form-urlencoded; charset=UTF-8",
    "X-Frappe-CSRF-Token" -> "None",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_4 = Map("Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val headers_5 = Map(
    "Content-Type" -> "application/x-www-form-urlencoded; charset=UTF-8",
    "X-Requested-With" -> "XMLHttpRequest")


  object Home {
    val openLoginPage: ChainBuilder =
      exec(http("MainPage")
        .get("/")
        .headers(headers_0))
        .exec(http("LoginPage")
          .get("/login")
          .headers(headers_0)
          .check(status.is(200)))
        .exec(http("LoginProcess")
          .post("/")
          .headers(headers_2)
          .formParam("cmd", "login")
          .formParam("usr", "qw1@er.ty")
          .formParam("pwd", "123456")
          .formParam("device", "desktop")
          .check(status.is(200)))
        .exec(http("Get Buying Module")
          .post("/")
          .headers(headers_5)
          .formParam("module", "Buying")
          .formParam("cmd", "frappe.desk.moduleview.get")
          .check(status.is(200)))
  }
  object Selling {
    val openSellingModule: ChainBuilder =
      exec(http("Open Selling Module")
        .post("/")
        .headers(headers_0)
        .formParam("module", "Selling")
        .formParam("cmd", "frappe.desk.moduleview.get"))
    val SaveSellOrder: ChainBuilder =
        exec(http("Get Selling Items")
          .get("/?txt=&doctype=Item&query=erpnext.controllers.queries.item_query&filters=%7B%22is_sales_item%22%3A1%7D&cmd=frappe.desk.search.search_link&_=1489874137561")
          .headers(headers_5)
          .check(status.is(200), bodyString.saveAs("sellItemsList")))
        .exec(http("Get Water Details")
          .post("/")
          .headers(headers_0)
          .formParam("args", session => gson.toJson(
            new SellingItems(SellingItemModel.deserialize(session("sellItemsList").as[String]).results.get(0).value)))
          .formParam("cmd", "erpnext.stock.get_item_details.get_item_details")
          .check(status.is(200), bodyString.saveAs("itemWater")))
        .exec(http("Get Water Details")
          .post("/")
          .headers(headers_0)
          .formParam("args", session => gson.toJson(
            new SellingItems(SellingItemModel.deserialize(session("sellItemsList").as[String]).results.get(1).value)))
          .formParam("cmd", "erpnext.stock.get_item_details.get_item_details")
          .check(status.is(200), bodyString.saveAs("itemAir")))
        .exec(http("Save and Submit Sales Order")
          .post("/")
          .headers(headers_2)
          .formParam("doc", session => SellingSubmitModel.serializeSaveModel(
            PurchaseItemModel.deserializeItem(session("itemWater").as[String]),
            PurchaseItemModel.deserializeItem(session("itemAir").as[String])))
          .formParam("action", "Submit")
          .formParam("cmd", "frappe.desk.form.save.savedocs")
          .check(jsonPath("$.docinfo.communications[:1].reference_name").saveAs("salesOrderName")))
        .exec(http("Map Delivery Order")
          .post("/")
          .headers(headers_0)
          .formParam("method", "erpnext.selling.doctype.sales_order.sales_order.make_delivery_note")
          .formParam("source_name", session => session("salesOrderName").as[String])
          .formParam("selected_children", "{}")
          .formParam("cmd", "frappe.model.mapper.make_mapped_doc")
          .resources(
            http("Save Delivery Order")
              .post("/")
              .headers(headers_5)
              .formParam("doc", session => DeliveryOrderModel.serializeSaveModel(session("salesOrderName").as[String],
                PurchaseItemModel.deserializeItem(session("itemWater").as[String]),
                PurchaseItemModel.deserializeItem(session("itemAir").as[String])))
              .formParam("action", "Submit")
              .formParam("cmd", "frappe.desk.form.save.savedocs")
              .check(jsonPath("$.docinfo.communications[:1].reference_name").saveAs("deliveryOrderName"))))
        .exec(http("Close Delivery Order")
          .post("/")
          .headers(headers_5)
          .formParam("docname", session => session("deliveryOrderName").as[String])
          .formParam("status", "Closed")
          .formParam("cmd", "erpnext.stock.doctype.delivery_note.delivery_note.update_delivery_note_status"))
        .exec(http("Mapped Invoice")
          .post("/")
          .headers(headers_0)
          .formParam("method", "erpnext.selling.doctype.sales_order.sales_order.make_sales_invoice")
          .formParam("source_name", session => session("salesOrderName").as[String])
          .formParam("selected_children", "{}")
          .formParam("cmd", "frappe.model.mapper.make_mapped_doc")
          .resources(
            http("Save Invoice")
              .post("/")
              .headers(headers_0)
              .formParam("doc", session => InvoiceSubmitModel.serializeSaveModel(session("salesOrderName").as[String],
                PurchaseItemModel.deserializeItem(session("itemWater").as[String]),
                PurchaseItemModel.deserializeItem(session("itemAir").as[String])))
              .formParam("action", "Submit")
              .formParam("cmd", "frappe.desk.form.save.savedocs")
              .check(jsonPath("$.docinfo.communications[:1].reference_name").saveAs("invoiceName"))))
        .exec(http("Mapped payment Entry")
          .post("/")
          .headers(headers_0)
          .formParam("dt", "Sales Invoice")
          .formParam("dn", session => session("invoiceName").as[String])
          .formParam("cmd", "erpnext.accounts.doctype.payment_entry.payment_entry.get_payment_entry"))
        .exec(http("Create Payment Entry")
          .post("/")
          .headers(headers_0)
          .formParam("doc", session => PaymentModel.serializeSaveModel(session("invoiceName").as[String]))
          .formParam("action", "Submit")
          .formParam("cmd", "frappe.desk.form.save.savedocs")
          .check(jsonPath("$.docinfo.communications[:1].reference_name").saveAs("paymentName")))

    val RemoveItems: ChainBuilder =
      exec(
        http("Cancel Payment")
          .post("/")
          .headers(headers_5)
          .formParam("doctype", "Payment Entry")
          .formParam("name", session => session("paymentName").as[String])
          .formParam("cmd", "frappe.desk.form.save.cancel"))
        .exec(http("Delete Payment")
          .post("/")
          .headers(headers_5)
          .formParam("doctype", "Payment Entry")
          .formParam("name", session => session("paymentName").as[String])
          .formParam("cmd", "frappe.client.delete"))
        .exec(
          http("Cancel Invoice")
            .post("/")
            .headers(headers_5)
            .formParam("doctype", "Sales Invoice")
            .formParam("name", session => session("invoiceName").as[String])
            .formParam("cmd", "frappe.desk.form.save.cancel"))
        .exec(http("Delete Invoice")
          .post("/")
          .headers(headers_5)
          .formParam("doctype", "Sales Invoice")
          .formParam("name", session => session("invoiceName").as[String])
          .formParam("cmd", "frappe.client.delete"))
        .exec(
          http("Cancel Delivery")
            .post("/")
            .headers(headers_5)
            .formParam("doctype", "Delivery Note")
            .formParam("name", session => session("deliveryOrderName").as[String])
            .formParam("cmd", "frappe.desk.form.save.cancel"))
        .exec(http("Delete Delivery")
          .post("/")
          .headers(headers_5)
          .formParam("doctype", "Delivery Note")
          .formParam("name", session => session("deliveryOrderName").as[String])
          .formParam("cmd", "frappe.client.delete"))
        .exec(
          http("Cancel Sales Order")
            .post("/")
            .headers(headers_5)
            .formParam("doctype", "Sales Order")
            .formParam("name", session => session("salesOrderName").as[String])
            .formParam("cmd", "frappe.desk.form.save.cancel"))
        .exec(http("Delete Sales Order")
          .post("/")
          .headers(headers_5)
          .formParam("doctype", "Sales Order")
          .formParam("name", session => session("salesOrderName").as[String])
          .formParam("cmd", "frappe.client.delete"))
        .exec(session => {
          println("=========================!!!!!!!Sales Order!!!!!!!===========================")
          println(session("salesOrderName").as[String])
          println("=========================!!!!!!!Delivery Order!!!!!!!===========================")
          println(session("deliveryOrderName").as[String])
          println("=========================!!!!!!!Sales Invoice!!!!!!!===========================")
          println(session("invoiceName").as[String])
          println("=========================!!!!!!!Payment Entry!!!!!!!===========================")
          println(session("paymentName").as[String])
          session
        })
  }


}
