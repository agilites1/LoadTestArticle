package Models.Selling

import java.text.SimpleDateFormat
import java.util.Calendar

/**
  * Created by artyom on 3/16/2017.
  */
class SellingItems(code:String) {
  val item_code: String = code
  val barcode = null
  val customer = "Customer 1"
  val currency = "USD"
  val conversion_rate = 1
  val price_list = "Standard Selling"
  val price_list_currency = "USD"
  val plc_conversion_rate = 1
  val company = "mytestcompany"
  val order_type = "Sales"
  val is_pos = 0
  val transaction_date: String = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime)
  val doctype = "Sales Order"
  val name = "New Sales Order 1"
  val qty = 0
}
