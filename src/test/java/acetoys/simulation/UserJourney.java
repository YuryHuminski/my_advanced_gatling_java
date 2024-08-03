package acetoys.simulation;

import java.time.Duration;
import acetoys.pageobjects.*;
import static acetoys.session.UserSession.*;
import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;

public class UserJourney {

    private static final Duration LOW_PAUSE = Duration.ofMillis(1000);
    private static final Duration HIGH_PAUSE = Duration.ofMillis(3000);

    public static ChainBuilder browserStore =
            exec(initSession)
                    .exec(StaticPages.homePage)
                    .pause(LOW_PAUSE)
                    .exec(StaticPages.ourStoryPage)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(StaticPages.getInTouchPage)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .repeat(3).on(
                            exec(Category.getCategory)
                                    .pause(LOW_PAUSE, HIGH_PAUSE)
                                    .exec(Category.cyclePagesOfProducts)
                                    .pause(LOW_PAUSE, HIGH_PAUSE)
                                    .exec(Product.getPDP)
                    );

    public static ChainBuilder abandonBasket =
            exec(initSession)
                    .exec(StaticPages.homePage)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Category.getCategory)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Product.getPDP)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Product.addToCart);

    public static ChainBuilder completePurchase =
            exec(initSession)
                    .exec(StaticPages.homePage)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Category.getCategory)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Product.getPDP)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Product.addToCart)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Cart.viewCart)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .repeat(2)
                        .on(Cart.increaseProductQuantity
                        .pause(LOW_PAUSE, HIGH_PAUSE))
                    .exec(Cart.decreaseProductQuantity)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Cart.checkout)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Customer.logout);

}
