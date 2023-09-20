package com.melik.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author mselvi
 * @Created 18.09.2023
 */

@RestController
@RequestMapping("/resillience4j")
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    /*
     * application properties de 5 kere denenceğini gösterdik. Her denemeda fake url'e gittiği için hata alıcak ve fallbackmethod çalışacak
     * */
    @GetMapping("/retry")
    @Retry(name = "retry", fallbackMethod = "fallbackMethod")
    public String retry() {
        logger.info("Sample Api Call Retry");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return forEntity.getBody();
    }

    /*
     * watch -n 0.1 curl http://localhost:8000/sample-api  komutu ile saniyede 10 tane request atabiliriz.
     * */
    @GetMapping("/circuit-breaker")
    @CircuitBreaker(name = "circuit", fallbackMethod = "fallBackMethod")
    public String circuitBreaker() {
        logger.info("Sample Api Call Circuit Breaker");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return forEntity.getBody();
    }

    /*
    * Request'lere sınır koyuyoruz.
    * */
    @GetMapping("/rate-limiter")
    @RateLimiter(name = "rate")
    public String rateLimiter() {
        logger.info("Sample Api Call Rate Limiter");
        return "rate-limiter";
    }

    /*
     * Eş zamanlı Request'lere tepe değeri getirdik.
     * */
    @GetMapping("/bulkhead")
    @Bulkhead(name = "bulk")
    public String bulkhead() {
        logger.info("Sample Api Call Bulkhead");
        return "rate-limiter";
    }

    public String fallbackMethod(Exception exception) {
        return "fallback-response";
    }
}
