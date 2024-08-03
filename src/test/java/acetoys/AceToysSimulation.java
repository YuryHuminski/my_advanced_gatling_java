package acetoys;

import acetoys.simulation.TestPopulation;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AceToysSimulation extends Simulation {

  private static final String TEST_TYPE = System.getProperty("TEST_TYPE", "INSTANT_USERS");

  private static final String DOMAIN = "acetoys.uk";

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://" + DOMAIN)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("be-BY,be;q=0.9,en-US;q=0.8,en;q=0.7,ru;q=0.6");

  // Set-up load parameters of test
  {
    switch (TEST_TYPE) {
//      case ("INSTANT_USERS"): setUp(TestPopulation.instantUsers).protocols(httpProtocol);
//        break;
      case ("RAMP_USERS"): setUp(TestPopulation.rampUsers).protocols(httpProtocol);
        break;
      case ("COMPLEX_INJECTION"): setUp(TestPopulation.complexInjection).protocols(httpProtocol);
        break;
      case ("CLOSED_MODEL"): setUp(TestPopulation.closedModel).protocols(httpProtocol);
        break;
      default: setUp(TestPopulation.instantUsers).protocols(httpProtocol);
    }
  }
}
