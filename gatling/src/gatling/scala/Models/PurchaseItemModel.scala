package Models

import com.google.gson.Gson

/**
  * Created by artyom on 3/25/2017.
  */
object PurchaseItemModel {
  class Items {
    var docstatus: Int = 0
    var doctype: String = ""
    var name: String = ""
    var __islocal: Int = 1
    var __unsaved: Int = 1
    var owner: String = "qw1@er.ty"
    var stock_uom: String = _
    var margin_type: String = ""
    var parent: String = ""
    var parentfield: String = "items"
    var parenttype: String = ""
    val idx: Int = 2
    var __unedited: Boolean = false
    var item_code: String = _
    var barcode: String = _
    var valuation_rate: Int = 1
    var item_group: String = _
    var description: String = _
    var stock_qty: Int = 0
    var gross_profit: Int = 0
    var base_price_list_rate: Int = 50
    var image: String = _
    var base_amount: Int = 50
    var expense_account: String = _
    var projected_qty: Int = 0
    var item_tax_rate: String = _
    var rate: Int = 50
    var brand: String = _
    var cost_center: String = _
    var supplier: String = _
    var net_amount = 0
    var price_list_rate: Int = 0
    var pricing_rule: String = _
    var income_account: String = _
    var item_name: String = _
    var amount = 0
    var discount_percentage = 0
    var qty = 0
    var min_order_qty: String = _
    var actual_qty = 0
    var net_rate = 0
    var conversion_factor = 0
    var customer_item_code: String = _
    var batch_no: String = _
    var warehouse: String = _
    var delivered_by_supplier = 0
    var base_rate = 0
    var is_fixed_asset = 0
    var uom: String = _
    var total_margin = 0
    var margin_rate_or_amount = 0
    var base_net_rate = 0
    var base_net_amount = 0
    var ordered_qty = 0
    var delivered_qty = 0
    var returned_qty = 0
    var billed_amt = 0
    var planned_qty = 0
    var produced_qty = 0
    var against_sales_order:String = _
    var sales_order:String = _

    var is_sample_item = 0
  }

  case class PurchaseItem(message: Items)

  def deserializeItem(json: String): Items = {
    val item = new Gson().fromJson(json, classOf[PurchaseItem])
    item.message.doctype = "Sales Order Item"
    item.message.name = "New Sales Order Item 2"
    item.message
  }
}
