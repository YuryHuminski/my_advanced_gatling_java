package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

import static acetoys.session.UserSession.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Cart {

    public static ChainBuilder viewCart =
            doIf(session -> !session.getBoolean("customerLoggedIn"))
                    .then(Customer.login)
                    .exec(
                        http("Open Cart")
                            .get("/cart/view") 
                                .check(css("#CategoryHeader").is("Cart Overview"))
            );

    public static ChainBuilder increaseProductQuantity =
            exec(increaseItemsInBasketForSession)
            .exec(increaseSessionBasketTotal)
                    .exec(
                    http("Increase product quantity (Product name: #{name})")
                            .get("/cart/add/#{id}?cartPage=true")
                            .check(css("#grandTotal").isEL("$#{basketTotal}"))
            );

    public static ChainBuilder decreaseProductQuantity =
            exec(decreaseItemsInBasketForSession)
            .exec(decreaseSessionBasketTotal)
                    .exec(
                    http("Decrease product quantity (Product name: #{name})")
                            .get("/cart/subtract/#{id}")
                            .check(css("#grandTotal").isEL("$#{basketTotal}"))
            );

    public static ChainBuilder checkout =
            exec(
                    http("Checkout")
                            .get("/cart/checkout")
                            .check(substring("Your products are on their way to you now!"))
            );

}
