package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;

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
            exec(
                    http("Increase product quantity (ProductID: 19)")
                            .get("/cart/add/19?cartPage=true")
            );

    public static ChainBuilder decreaseProductQuantity =
            exec(
                    http("Subtract product quantity (ProductID: 4)")
                            .get("/cart/subtract/19")
            );

    public static ChainBuilder checkout =
            exec(
                    http("Checkout")
                            .get("/cart/checkout")
                            .check(substring("Your products are on their way to you now!"))
            );

}
