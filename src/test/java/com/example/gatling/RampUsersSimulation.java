package com.example.gatling;

import com.example.configurations.ConfigLoader;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class RampUsersSimulation extends Simulation {

    private final String url = ConfigLoader.getProperty("base.url");

    HttpProtocolBuilder httpProtocol = http
            .baseUrl(url);

    ScenarioBuilder scn = scenario("Basic Smoke")
            .exec(
                    http("Home")
                            .get("/")
                            .check(status().is(200))
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(15))
        )
                .protocols(httpProtocol)
                .assertions(
                        global().successfulRequests().percent().gt(90.0)
                );
    }
}
