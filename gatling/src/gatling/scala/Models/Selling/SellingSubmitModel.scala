package Models.Selling

import java.text.SimpleDateFormat
import java.util.Calendar

import Models.{PurchaseItemModel, SaveDockModel}
import com.google.gson.GsonBuilder

/**
  * Created by artyom on 3/20/2017.
  */
object SellingSubmitModel {
  def serializeSaveModel(items: PurchaseItemModel.Items*): String = {
    val gson = new GsonBuilder().create
    var sum: Int = 0
    items.foreach(sum += _.rate)
    val saveModel = new SaveDockModel.SubmitModel(sum, items.toArray)
    saveModel.doctype = "Sales Order"
    saveModel.naming_series = "SO-"
    saveModel.billing_status = "Not Billed"
    saveModel.delivery_status = "Not Delivered"
    saveModel.order_type = "Sales"
    gson.toJson(saveModel)
  }

}
