package com.melik.currencyexchangeservice.controller;

import com.melik.currencyexchangeservice.bean.CurrencyExchange;
import com.melik.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mselvi
 * @Created 17.09.2023
 */

@RestController
@RequestMapping
public class CurrencyExchangeController {

    private Logger logger= LoggerFactory.getLogger(CurrencyExchangeController.class);

    private final Environment environment;
    private final CurrencyExchangeRepository repository;

    public CurrencyExchangeController(Environment environment, CurrencyExchangeRepository repository) {
        this.environment = environment;
        this.repository = repository;
    }

    @GetMapping
    public String hello(){
        return "Welcome";
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        /*
        * bu kod satırı çalıştığında log info olarak zipkin pattern'i ile birlikte bu mesaj verilecek.
        * zipkin pattern'inde ilk uygulama adı sonra traceId sonra da spanId gösterilecek. Bu pattern'i application.properties'de verdik.
        * traceId her bir request'e ait olan unique id değeridir.
        * */
        logger.info("retrieveExchangeValue called with {] to {}",from,to);
        String property = environment.getProperty("local.server.port");

        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
        currencyExchange.setEnvironment(property);
        return currencyExchange;
    }

}
