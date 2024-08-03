package acetoys.simulation;

import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;

public class TestScenario {

    public static final Duration TEST_DURATION = Duration.ofSeconds(Integer.parseInt(System.getProperty("TEST_DURATION", "60")));

    public static ScenarioBuilder defaultLoadTest =
            scenario("Default Load Test")
                    .during(TEST_DURATION)
                    .on(
                            randomSwitch()
                                    .on(
                                            Choice.withWeight(60, UserJourney.browserStore),
                                            Choice.withWeight(30, UserJourney.abandonBasket),
                                            Choice.withWeight(10, UserJourney.completePurchase)
                                    )
                    );

    public static ScenarioBuilder highPurchaseLoadTest =
            scenario("High Purchase Load Test")
                    .during(TEST_DURATION)
                    .on(
                            randomSwitch()
                                    .on(
                                            Choice.withWeight(30, UserJourney.browserStore),
                                            Choice.withWeight(30, UserJourney.abandonBasket),
                                            Choice.withWeight(40, UserJourney.completePurchase)
                                    )
                    );
}
