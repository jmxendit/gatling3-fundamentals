package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class JMRuntimeParameters extends  Simulation{

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

//  def getSpecificGame() = {
//    exec(
//      http("Get Specific Game")
//        .get("videogames/2")
//        .check(status.is(200))
//    )
//  }

  val scn = scenario("Fixed Duration Load Simulation")
    .forever(){
      exec(getAllVideoGames())
    }


  setUp(
    scn.inject(
      nothingFor(5 seconds),
      rampUsers(1) during (1)
    )
  ).protocols(httpConf.inferHtmlResources())
    .maxDuration(20 seconds)
}
