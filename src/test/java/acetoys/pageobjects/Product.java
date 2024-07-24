package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static acetoys.session.UserSession.increaseItemsInBasketForSession;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Product {

    private static final FeederBuilder<Object> jsonFeeder =
            jsonFile("data/productDetails.json").random();

    public static ChainBuilder getPDP =
            feed(jsonFeeder)
                    .exec(
                            http("PDP: #{name}")
                                .get("/product/#{slug}")
                                .check(css("h2").isEL("#{name}"), css("#ProductDescription").isEL("#{description}"))
                    );

    public static ChainBuilder addToCart =
            exec(increaseItemsInBasketForSession )
                    .exec(
                            http("Add to cart: #{name}")
                                .get("/cart/add/#{id}")
                                .check(substring("You have <span>#{itemsInBasket}</span> products in your Basket."))
            );
}
