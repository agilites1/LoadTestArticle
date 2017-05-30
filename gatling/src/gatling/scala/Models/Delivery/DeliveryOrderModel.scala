package Models.Delivery

import java.text.SimpleDateFormat
import java.util.Calendar

import Models.{PurchaseItemModel, SaveDockModel}
import com.google.gson.GsonBuilder

/**
  * Created by artyom on 3/25/2017.
  */
object DeliveryOrderModel {
  def serializeSaveModel(sellingItemName: String, items: PurchaseItemModel.Items*): String = {
    val gson = new GsonBuilder().create
    var sum: Int = 0
    items.foreach(sum += _.rate)
    items.foreach(x => x.against_sales_order = sellingItemName)
    items.foreach(x => x.parenttype = "Delivery Note")
    items.foreach(x => x.parent = "New Delivery Note 3")
    items.foreach(x => x.doctype = "Delivery Note Item")
    val saveModel = new SaveDockModel.SubmitModel(sum, items.toArray)
    saveModel.doctype = "Delivery Note"
    saveModel.naming_series = "DN-"
    gson.toJson(saveModel)
  }
}
