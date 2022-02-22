package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt

class JMAddPauseTimer extends  Simulation{

  // 1 Http Conf
  val httpConf = http.baseUrl(("http://localhost:8080/app/"))
    .header("Accept", "application/json")
    .proxy(Proxy("localhost", 8866))

  // 2 Scenario Definition
  val scn = scenario("Video Game DB - 3 calls")
    .exec(http("Get all video games - 1st call")
      .get("videogames"))
      .pause(5)

    .exec(http("Get Specific game")
    .get("videogames/1"))
    .pause(1, 20)

    .exec(http("Get all video games - 2nd call")
    .get("videogames"))
    .pause(3000.milliseconds)

  // 3 Load Scenario
  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}
