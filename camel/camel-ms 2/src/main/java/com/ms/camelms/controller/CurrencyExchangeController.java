package com.ms.camelms.controller;

import com.ms.camelms.entity.CurrencyExchange;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange findConversionValue(@PathVariable String from, @PathVariable String to) {
        return new CurrencyExchange(1000l,"USD","INR", BigDecimal.valueOf(82));
    }
}
