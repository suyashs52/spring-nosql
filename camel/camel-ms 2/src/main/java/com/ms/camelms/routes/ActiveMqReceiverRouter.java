package com.ms.camelms.routes;

import com.ms.camelms.entity.CurrencyExchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.*;
import java.security.cert.CertificateException;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

    @Autowired
    CurrencyExchangeProcessor myCurrencyExchangeProcessor;

    @Autowired
    CurrencyExchangeTransformer currencyExchangeTransformer;
    @Override
    public void configure() throws Exception {

//
//        from("activemq:my-activemq-queue")
//                .unmarshal().json(JsonLibrary.Jackson,CurrencyExchange.class)
//                .bean(myCurrencyExchangeProcessor)
//                .bean(currencyExchangeTransformer)
//                .to("log:received-message-from-queue")
//                .log("${body}");

//        from("activemq:my-activemq-xml-queue")
//                .unmarshal()
//                .jacksonxml(CurrencyExchange.class)
//
//                .bean(myCurrencyExchangeProcessor)
//                .bean(currencyExchangeTransformer)
//                .to("log:received-message-from-queue")
//                .log("${body}");
         from("activemq:my-activemq-queue")
                 .log("${body}")
                 .unmarshal(createEncryptor())
                 .to("log:received-message-from-active-mq")
                 .log("${body}");


    }


    private CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        ClassLoader classLoader = getClass().getClassLoader();
        keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), "someKeystorePassword".toCharArray());
        Key sharedKey = keyStore.getKey("myDesKey", "someKeyPassword".toCharArray());

        CryptoDataFormat sharedKeyCrypto = new CryptoDataFormat("DES", sharedKey);
        return sharedKeyCrypto;
    }
}

@Component
class CurrencyExchangeProcessor{
    Logger logger= LoggerFactory.getLogger(ActiveMqReceiverRouter.class);


    public void processMessage(CurrencyExchange currencyExchange){


        logger.info("Do some processing with currency exchange which is {}",currencyExchange.getConversionMultiple());
    }


}



@Component
class CurrencyExchangeTransformer{
    Logger logger= LoggerFactory.getLogger(ActiveMqReceiverRouter.class);


    public CurrencyExchange processMessage(CurrencyExchange currencyExchange){

        currencyExchange.setConversionMultiple(currencyExchange.getConversionMultiple().multiply(BigDecimal.TEN));

        logger.info("Do some processing1 with currency exchange which is {}",currencyExchange.getConversionMultiple());

        return currencyExchange;
    }


}