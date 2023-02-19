package com.ms.camelms.routes;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
public class FileRouter extends RouteBuilder {

    @Autowired
    private DeciderBean deciderBean;

    @Override
    public void configure() throws Exception {
        //Pipeline Pattern: default pattern
        from("file:files/input")
                .routeId("Files-Input-Route") //route id
                .transform().body(String.class)//convert body content to string
                .choice() //Content Based Routing Pattern
                .when(method(deciderBean))
                .log("check login in bean...")
                .when(simple("${file:ext} ends with 'json'")) //condition
                .log("JSON File")
                .when(simple("${body} contains 'USD'")) //condition
                .log("Contains USD")
                .otherwise()
                .log("Not a json file")

                .end()
                //  .to("direct://log-file-values")
                .to("file:files/output");

        from("direct:log-file-values")
                .log("${body}")
                .log("${messageHistory}")
                .log("${messageHistory} ${headers}")
                .log("${file:path} ${file:absolute}")
                .log("${routeId} ${camelId} ${body}");

    }
}

@Component
class DeciderBean {
    Logger logger = LoggerFactory.getLogger(DeciderBean.class);

    public boolean isThisConditionMet(@Body String body
            , @Headers Map<String, String> headers
            , @ExchangeProperties Map<String, String> exchanges
    ) {
        logger.info("DeciderBean {} Headers {} \n Exchange {}", body, headers,exchanges);
        return true;
    }
}
