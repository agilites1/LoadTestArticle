package Models

import java.text.SimpleDateFormat
import java.util.Calendar

/**
  * Created by artyom on 3/25/2017.
  */
object SaveDockModel {

  class SubmitModel(totalSum: Int, itemList: Array[PurchaseItemModel.Items]) {
    var billing_status: String = _
    val modified_by: String = "qw1@er.ty"
    val title: String = "Customer 1"
    val packed_items = new Array[String](0)
    val selling_price_list: String = "Standard Selling"
    val tc_name: String = null
    val source: String = null
    val base_grand_total: Int = totalSum
    val submit_on_creation: Int = 0
    val base_in_words: String = ""
    var doctype: String = _
    var ignore_pricing_rule: Int = 0
    val base_discount_amount: Int = 0
    val notify_by_email: Int = 0
    val base_total_taxes_and_charges: Int = 0
    val items: Array[PurchaseItemModel.Items] = itemList
    val discount_amount: Int = 0
    var name: String = ""
    val taxes = new Array[String](0)
    val select_print_heading: String = null
    val delivery_date: String = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime)
    val parentfield: String = null
    val creation: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime)
    val party_account_currency: String = null
    val net_total: Int = totalSum
    val po_date: String = null
    val price_list_currency: String = "USD"
    val contact_display: String = "Customer 1"
    val next_date: String = null
    val terms: String = null
    val parent: String = null
    val advance_paid: Int = 0
    val customer_address: String = null
    val total_commission: Int = 0
    val contact_mobile: String = null
    var delivery_status: String = _
    val base_net_total: Int = totalSum
    val anguage: String = "en-US"
    val rounded_total: Int = totalSum
    val shipping_address_name: String = null
    val apply_discount_on: String = "Grand Total"
    val po_no: String = null
    val repeat_on_day_of_month: Int = 0
    val contact_person: String = "Customer 1-Customer 1"
    val in_words: String = ""
    val recurring_print_format: String = null
    val additional_discount_percentage: Int = 0
    val campaign: String = null
    val conversion_rate: Int = 1
    val to_date: String = null
    val owner: String = "qw1@er.ty"
    val total: Int = totalSum
    val customer_name: String = "Customer 1"
    val commission_rate: Int = 0
    val base_total: Int = totalSum
    val from_date: String = null
    val territory: String = "Ukraine"
    val sales_partner: String = null
    val end_date: String = null
    val company: String = "mytestcompany"
    val base_rounded_total: Int = totalSum
    val customer: String = "Customer 1"
    val grand_total: Int = totalSum
    val notification_email_address: String = null
    val is_recurring: Int = 0
    val idx: Int = 0
    val modified: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime)
    val project: String = null
    val shipping_address: String = null
    val customer_group: String = "Commercial"
    val address_display: String = null
    var naming_series: String = _
    val currency: String = "USD"
    val letter_head: String = null
    val recurring_id: String = null
    val shipping_rule: String = null
    var order_type: String = _
    val amended_from: String = null
    val transaction_date: String = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime)
    val recurring_type: String = ""
    val docstatus: Int = 0
    val per_delivered: Int = 0
    val status: String = "Draft"
    val group_same_items: Int = 0
    val __onload: String = """{'make_payment_via_journal_entry': 0},"""
    val taxes_and_charges: String = null
    val per_billed = 0
    val plc_conversion_rate: Int = 1
    val parenttype: String = null
    val total_taxes_and_charges: Int = 0
    val contact_email: String = null
    val sales_team = new Array[String](0)
    val __last_sync_on: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime)

    val base_write_off_amount = 0
    val is_return = 0
    val write_off_outstanding_amount_automatically = 0
    val is_pos = 0
    val against_income_account = "Sales - MC"
    val write_off_amount = 0
    val timesheets = new Array[String](0)
    val payments = new Array[String](0)
    val c_form_applicable = "No"
    var debit_to: String = _
    val base_paid_amount = 0
    val posting_time: String = new SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().getTime)
    val update_stock = 0
    var is_opening: String = _
    val posting_date: String = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime)
    val advances = new Array[String](0)
    var remarks: String = _
    val due_date: String = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime)
  }

}
