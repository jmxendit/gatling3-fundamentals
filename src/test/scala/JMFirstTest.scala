import io.gatling.core.Predef._
import io.gatling.http.Predef._

class JMFirstTest extends  Simulation {

  //1 Http Conf
  val httpConf = http.baseUrl(("http://localhost:8080/app/"))
  .header("Accept", "application/json")
    .proxy(Proxy("localhost", 8866))

  //2 Scenario Definition
  val scn = scenario("My First Test JM")
    .exec(http("Get All Games")
    .get("videogames"))

  //3 Load Scenario
  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}
