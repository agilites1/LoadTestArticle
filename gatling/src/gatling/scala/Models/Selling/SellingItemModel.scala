package Models.Selling

import com.google.gson.{Gson, GsonBuilder}

/**
  * Created by artyom on 3/17/2017.
  */
object SellingItemModel {

  case class Child(description: String, value: String)

  case class Results(results: java.util.ArrayList[Child])

  def deserialize(json: String): Results = {
    new Gson().fromJson(json, classOf[Results])
  }

  def serialize(p: Results): String = {
    new Gson().toJson(p)
  }

  def prettyPrint(p: Results): String = {
    new GsonBuilder().setPrettyPrinting().create().toJson(p)
  }
}
