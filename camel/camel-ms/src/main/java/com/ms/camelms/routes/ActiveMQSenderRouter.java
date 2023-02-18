package com.ms.camelms.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQSenderRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

//        from("timer:first-timer")
//                .transform().constant("test")
//                .log("${body}")
//                        .to("log:first-timer");
        //timer for 10 sec
        from("timer:active-mq-timer?period=10000")
                .transform().constant("My message for Active MQ")
                .log("${body}")
                .to("activemq:my-activemq-queue");
    }
}
