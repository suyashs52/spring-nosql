package com.ms.camelms.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

@Component
public class ActiveMQSenderRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

//        from("timer:first-timer")
//                .transform().constant("test")
//                .log("${body}")
//                        .to("log:first-timer");
        //timer for 10 sec
//        from("timer:active-mq-timer?period=10000")
//                .transform().constant("My message for Active MQ")
//                .log("${body}")
//                .to("activemq:my-activemq-queue");

//        from("file:files/json")
//                .log("${body}")
//                .to("activemq:my-activemq-queue");

//        from("file:files/xml")
//                .log("${body}")
//                .to("activemq:my-activemq-xml-queue");

        from("timer:active-mq-timer?period=10000")
                .transform().constant("My message for Active MQ")
                .log("${body}")
                .marshal(createEncryptor())
                .log("${body}")

                .to("activemq:my-activemq-queue");

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
