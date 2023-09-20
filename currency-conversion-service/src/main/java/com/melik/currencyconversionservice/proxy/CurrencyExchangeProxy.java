package com.melik.currencyconversionservice.proxy;

import com.melik.currencyconversionservice.bean.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author mselvi
 * @Created 17.09.2023
 */

@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange-from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(@PathVariable String from,@PathVariable String to);
}
