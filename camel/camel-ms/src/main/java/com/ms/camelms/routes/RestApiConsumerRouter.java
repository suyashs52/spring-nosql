package com.ms.camelms.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class RestApiConsumerRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
    //every 10 sec call rest api
        restConfiguration().host("localhost").port(8000);
        from("timer:rest-api-consumer?period=10000")
                .log("${body}")
                .setHeader("from",()->"EUR")
                .setHeader("to",()->"INR")
                .to("rest:get:/currency-exchange/from/{from}/to/{to}")
        .log("${body}"); //print the value after the call
    }
}
