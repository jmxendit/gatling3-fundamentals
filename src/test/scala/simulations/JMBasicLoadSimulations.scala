package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class JMBasicLoadSimulations extends  Simulation{

  // 1 Http Conf
  val httpConf = http.baseUrl(("http://localhost:8080/app/"))
    .header("Accept", "application/json")

  def getAllVideoGames() = {
    exec(
      http("Get all video games")
        .get("videogames")
        .check(status.is(200))
    )
  }

  def getSpecificGame() = {
    exec(
      http("Get Specific Game")
        .get("videogames/2")
        .check(status.is(200))
    )
  }

  val scn = scenario("Basic Load Simulation")
    .exec(getAllVideoGames())
    .pause(5)
    .exec(getSpecificGame())
    .pause(5)
    .exec(getAllVideoGames())

    setUp(
      scn.inject(
        nothingFor(5 seconds),
        atOnceUsers(5),
        rampUsers(10) during (10 seconds)
      ).protocols(httpConf.inferHtmlResources())
    )

//  // 2 Scenario Definition
//  val scn = scenario("Video Game DB - 3 calls")
//    .exec(http("Get all video games - 1st call")
//      .get("videogames"))
//    .pause(5)
//
//    .exec(http("Get Specific game")
//      .get("videogames/1"))
//    .pause(1, 20)
//
//    .exec(http("Get all video games - 2nd call")
//      .get("videogames"))
//    .pause(3000.milliseconds)
//
//  // 3 Load Scenario
//  setUp(
//    scn.inject(atOnceUsers(1))
//  ).protocols(httpConf)

}
