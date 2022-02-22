package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt

class JMCheckResponseBody extends  Simulation{

  // 1 Http Conf
  val httpConf = http.baseUrl(("http://localhost:8080/app/"))
    .header("Accept", "application/json")

  // 2 Scenario Definition
  val scn = scenario("Check JSON PATH")
    .exec(http("Get a specific game")
      .get("videogames/2")
      .check(jsonPath("$.name").is("Gran Turismo 3")))

    .exec(http("Get all video games")
      .get("videogames")
      .check(jsonPath("$[1].id").saveAs("gameId")))
      .exec{session => println(session); session}

    .exec(http("Get a specific game")
    .get("videogames/${gameId}")
    .check(jsonPath("$.name").is("Gran Turismo 3"))
    .check(bodyString.saveAs("responseBody")))
    .exec{session => println(session("responseBody").as[String]); session}

  // 3 Load Scenario
  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}
