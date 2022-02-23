package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class JMRampUsersLoadSimulation extends  Simulation{

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

  //constant users per sec
//  setUp(
//    scn.inject(
//      nothingFor(5 seconds),
//      constantUsersPerSec(10) during (10 seconds)
//    ).protocols(httpConf.inferHtmlResources())
//  )

  //ramp users per sec
  setUp(
    scn.inject(
      nothingFor(5 seconds),
      rampUsersPerSec(1) to (5) during (20 seconds)
    ).protocols(httpConf.inferHtmlResources())
  )


}
