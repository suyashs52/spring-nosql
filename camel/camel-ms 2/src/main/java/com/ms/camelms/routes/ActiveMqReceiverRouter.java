package com.ms.camelms.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("activemq:my-activemq-queue")
                .to("log:received-message-from-queue")
                .log("${body}");

    }
}
