package acetoys;

import acetoys.pageobjects.*;

import acetoys.session.UserSession;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AceToysSimulation extends Simulation {

  private static final String DOMAIN = "acetoys.uk";

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://" + DOMAIN)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("be-BY,be;q=0.9,en-US;q=0.8,en;q=0.7,ru;q=0.6");

  private ScenarioBuilder scn = scenario("AceToysSimulation")
          .exec(UserSession.initSession)
    .exec(StaticPages.homePage)
    .pause(2)
    .exec(StaticPages.ourStoryPage)
    .pause(2)
    .exec(StaticPages.getInTouchPage)
    .pause(2)
    .exec(Category.getCategory)
    .pause(2)
    .exec(Category.cyclePagesOfProducts)
    .pause(2)
    .exec(Product.getPDP)
    .pause(2)
    .exec(Product.addToCart)
    .pause(2)
    .exec(Category.getCategory)
    .pause(2)
    .exec(Product.addToCart)
    .pause(2)
    .exec(Product.addToCart)
    .pause(2)
    .exec(Cart.viewCart)
    .pause(2)
//          .exec(
//            session -> {
//              System.out.println(session);
//              System.out.println("csrfTokenLogout is: " + session.getString("csrfTokenLogout"));
//              return session;
//            }
//          )
    .repeat(2)
        .on(Cart.increaseProductQuantity
        .pause(2))
    .exec(Cart.decreaseProductQuantity )
    .pause(2)
          .exec(Cart.viewCart)
          .pause(2)
    .exec(Cart.checkout)
    .pause(2)
    .exec(Customer.logout);

  // Set-up load parameters of test
  {
	  setUp(
         scn.injectOpen(
            atOnceUsers(1)
         )
      ).protocols(httpProtocol);
  }
}
