package Models.Invoice

import Models.{PurchaseItemModel, SaveDockModel}
import com.google.gson.GsonBuilder

/**
  * Created by artyom on 3/26/2017.
  */
object InvoiceSubmitModel {
  def serializeSaveModel(sellingItemName: String,items: PurchaseItemModel.Items*): String = {
    val gson = new GsonBuilder().create
    var sum: Int = 0
    items.foreach(sum += _.rate)
    items.foreach(x => x.against_sales_order = sellingItemName)
    items.foreach(x => x.sales_order = sellingItemName)
    items.foreach(x => x.parenttype = "Sales Invoice")
    items.foreach(x => x.doctype = "Sales Invoice Item")
    val saveModel = new SaveDockModel.SubmitModel(sum, items.toArray)
    saveModel.doctype = "Sales Invoice"
    saveModel.naming_series = "SINV-"
    saveModel.ignore_pricing_rule = 1
    saveModel.debit_to = "Debtors - MC"
    saveModel.is_opening = "No"
    saveModel.remarks = "No Remarks"
    gson.toJson(saveModel)
  }
}
