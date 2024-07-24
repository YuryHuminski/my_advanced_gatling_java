package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class StaticPages {

    public static ChainBuilder homePage =
            exec(
                    http("Home Page")
                            .get("/")
                            .check(status().is(200))
                            .check(status().not(404), status().not(405))
                            .check(substring("<title>Ace Toys Online Shop</title>"))
                            .check(css("#_csrf", "content").saveAs("csrfTokenLogin"))
            );

    public static ChainBuilder ourStoryPage =
            exec(
                    http("Our Story Page")
                            .get("/our-story")
                            .check(regex("was founded online in \\d{4}"))
            );
    public static ChainBuilder getInTouchPage =
            exec(
                    http("Get In Touch Page")
                            .get("/get-in-touch")
                            .check(substring("There is not actually no way to get in touch with us, as we are not actually a real store!"))
            );
}
