package com.ms.camelms.routes;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Component

public class EipPatternsRouter extends RouteBuilder {

    @Autowired
    SplitterComponenet splitterComponenet;

    @Autowired
    DynamicRouterBean dynamicRouterBean;

    @Override
    public void configure() throws Exception {
        //error handle should be defined at start
        errorHandler(deadLetterChannel("activemq:dead-letter-queue"));
        //multi cast pattern: multiple end points to send
//        from("timer:multicast?period=10000")
//                .multicast()
//                .to("log:something1", "log:something2"); //2 log ll be priting , can be activemq,kafka
        //each of this get copy of the message

        //split pattern: example frm csv
//        from("file:files/csv")
//                .unmarshal().csv() //process line by line on csv using split
//                .split(body()) //each row
        //           .to("log:split-files");


//        from("file:files/csv")
//                .convertBodyTo(String.class)
//              //  .split(body(),",")
//                .split(method(splitterComponenet))
//                .to("log:split-files");

        //Aggregate Pattern:
        //Message>>Aggregate>>send to endpoint
        //to,3 > group by to 3 message at a time
        //for unmarshal json use json-starter
//        from("file:files/aggregate-json")
//                .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
//                .aggregate(simple("${body.to}"), new ArrayListAggregationStragegy())
//                .completionSize(3)
//                // .completionTimeout(HIGHEST) //wait for this time max
//                .convertBodyTo(String.class)
//                //  .split(body(),",")
//
//                .to("log:aggregate-json");

        //routing slip: which way to route
        String routingSlip = "direct:endpoint1,direct:endpoint2";
        // String routingSlip="direct:endpoint1,direct:endpoint2,,direct:endpoint3";

//        from("timer:routingSlip?period=10000")
//                .transform().constant("My Message is HardCoded")
//                .routingSlip(simple(routingSlip));


        //enable tracing
        getContext().setTracing(true); //to solve problem

        //no message is lost

        //wireTap("") //additional channel when information be sent too, like tapping a phone
        //Dynamic Routing Pattern: multiple end point , after each point decide which end ponit to send
        //until null get
        from("timer:routingSlip?period={{timePeriod}}")
                .transform().constant("My Message is HardCoded")
                .dynamicRouter(method(dynamicRouterBean));

        from("direct:endpoint1")
                .wireTap("log:wire-tap")
                .to("{{endpoint-for-logging}}");
        from("direct:endpoint2")
                .to("log:directendpoint2");
        from("direct:endpoint3")
                .to("log:directendpoint3");
    }
}

@Component
class DynamicRouterBean {
    Logger logger = LoggerFactory.getLogger(DynamicRouterBean.class);
    int invocation = 0;

    public String decideTheNextEndPoint(@ExchangeProperties Map<String, String> property,
                                        @Headers Map<String, String> headers,
                                        @Body String body) {

        logger.info("DynamicROuterBean {} {} {} ", property, headers, body);
        invocation++;
        if (invocation % 3 == 0)
            return "direct:endpoint1";
        else if (invocation % 3 == 1)
            return "direct:endpoint2,direct:endpoint3";

        return  null;


    }
}

class ArrayListAggregationStragegy implements AggregationStrategy {
    //1 , 2 , 3

    //starting have null,1
    //result,2
    //result,3

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        //  return newExchange; //return last one
        Object newBody = newExchange.getIn().getBody();
        ArrayList<Object> list = null;
        if (oldExchange == null) { //first time called set new array list, add body and return
            list = new ArrayList<Object>();
            list.add(newBody);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {//old exchange has 1,new exchange have 2 so combined it
            list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(newBody);
            return oldExchange;
        }
    }
}

@Component
class SplitterComponenet {
    public List<String> splitInput(String body) {
        return List.of("ABC", "DEF", "GHI");
    }
}
