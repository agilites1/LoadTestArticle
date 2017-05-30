import TestSimulation._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._

/**
  * Created by artyom on 4/4/2017.
  */
class LoadConstUsers extends Simulation{
  val users: ScenarioBuilder = scenario("Load with Constant Users")
    .exec(Home.openLoginPage, Selling.openSellingModule, Selling.SaveSellOrder,Selling.RemoveItems)
  setUp(users.inject(constantUsersPerSec(1) during(10 seconds))).protocols(httpProtocol)
}
