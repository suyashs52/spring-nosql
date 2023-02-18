package com.ms.camelms.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class FirstTimerRouter extends RouteBuilder
{

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;
    @Override
    public void configure() throws Exception {
        //get into queue, timer to trigger the messages
        //transform the result

        //save into database

        //starting point of the route
        //called timer endpoint: timer and log are keyworkds
        //Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
        from("timer:first-timer") //sending to queue message is null for now
                //.transform().constant("My constant message. Time now is "+ LocalDateTime.now())
              //  .bean("getCurrentTimeBean")
               // .bean(getCurrentTimeBean)
                .log("${body}")
               // .bean(getCurrentTimeBean,"getCurrentTime")
                //if something not changes body of message process if changing the body then tranform
                .transform().constant(getCurrentTimeBean.getCurrentTime())
                .log("${body}")
                .bean(getCurrentTimeBean,"getCurrentTime")
                .bean(simpleLoggingProcessingComponent) //void return so some kind of processing
                .log("${body}")
                .process(new SimpleLoggingProcessor())
                .to("log:first-timer"); //log is the end point
    }
}

@Component
class GetCurrentTimeBean{
    public String getCurrentTime(){
        return "Time Now is "+LocalDateTime.now();
    }


}

@Component
class SimpleLoggingProcessingComponent{
    private Logger logger= LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    public void process(String message){
        logger.info("SimpleLoggingProcessingComponent {}",message);
    }
}

class SimpleLoggingProcessor implements Processor{
    private Logger logger= LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("SimpleLoggingProcessor {}",exchange.getMessage().getBody());
    }
}