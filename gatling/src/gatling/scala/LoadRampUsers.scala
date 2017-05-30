import TestSimulation._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._

/**
  * Created by artyom on 4/4/2017.
  */
class LoadRampUsers extends Simulation{
  val users: ScenarioBuilder = scenario("Load with Ramp Users")
    .exec(Home.openLoginPage, Selling.openSellingModule, Selling.SaveSellOrder,Selling.RemoveItems)
  setUp(users.inject(rampUsers(10) over(5 seconds))).protocols(httpProtocol)
}
