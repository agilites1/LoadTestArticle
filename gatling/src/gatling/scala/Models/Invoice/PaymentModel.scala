package Models.Invoice

import java.text.SimpleDateFormat
import java.util.Calendar

import com.google.gson.GsonBuilder

/**
  * Created by artyom on 3/26/2017.
  */
object PaymentModel {
  class SubmitModel(totalSum: Int, itemList:Array[References]) {
    val total_allocated_amount: Int = totalSum
    val naming_series: String = "PE-"
    val mode_of_payment: String = null
    val target_exchange_rate = 1
    val paid_to: String = "mytestcompany - MC"
    val base_paid_amount: Int = totalSum
    val paid_to_account_currency: String = "USD"
    val reference_date: String = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime)
    val letter_head: String = null
    val reference_no: String = "123456"
    val references: Array[References] = itemList
    val owner: String = "qw1@er.ty"
    val print_heading: String = null
    val unallocated_amount = 0
    val __onload:String = """{'make_payment_via_journal_entry': 0},"""
    val allocate_payment_amount = 1
    val modified_by: String = "qw1@er.ty"
    val paid_amount: Int = totalSum
    val title: String = "Customer 1"
    val party_type: String = "Customer"
    val amended_from: String = null
    val base_total_allocated_amount: Int = totalSum
    val remarks: String = ""
    val party: String = "Customer 1"
    val base_received_amount: Int = totalSum
    val source_exchange_rate = 1
    val creation: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime)
    val doctype: String = "Payment Entry"
    val paid_from_account_balance = 0
    val parent: String = null
    val company: String = "mytestcompany"
    val paid_from: String = "Debtors - MC"
    val party_balance: Int = totalSum
    val deductions = new Array[String](0)
    val party_name: String = "Customer 1"
    val docstatus = 0
    val clearance_date: String = null
    val paid_from_account_currency: String = "USD"
    val paid_to_account_balance = 0
    val name: String = ""
    val idx = 0
    val difference_amount = 0
    val modified: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime)
    val received_amount: Int = totalSum
    val project: String = null
    val parenttype: String = null
    val payment_type: String = "Receive"
    val posting_date: String = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime)
    val parentfield: String = null
    val __last_sync_on: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime)
  }

  class References(totalSum: Int, referenceName:String) {
    val reference_doctype: String = "Sales Invoice"
    val total_amount: Int = totalSum
    val modified_by: String = "qw1@er.ty"
    val name: String = ""
    val parent: String = ""
    val due_date: String = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime)
    val reference_name: String = referenceName
    val creation: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime)
    val modified: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime)
    val outstanding_amount: Int = totalSum
    val idx = 1
    val parenttype: String = "Payment Entry"
    val doctype: String = "Payment Entry Reference"
    val owner: String = "qw1@er.ty"
    val docstatus = 0
    val allocated_amount: Int = totalSum
    val exchange_rate = 1
    val parentfield: String = "references"
  }

  def serializeSaveModel(invoiceName:String): String ={
    val gson = new GsonBuilder().create
    val sum = 150
    val saveModel = new SubmitModel(sum, Array.fill[References](1)(new References(sum, invoiceName)))
    gson.toJson(saveModel)
  }
}
