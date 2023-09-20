package com.melik.currencyconversionservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author mselvi
 * @Created 20.09.2023
 */

@Configuration
public class RestTemplateConfiguration {

    /*
     *RestTemplate'yi configuration class'ı içerisinde bean olarak application context'e yükleyip sonradan onu inject ederek kullanmak best practicedir.
     *Çünkü direkt olarak RestTemplate nesnesini kullanacağımız yerde oluşturup kullansak Distributed Tracing tarafından o RestTemplate nesnesini
     * kullanarak yaptığımız response'lar takip edilemiyor.
     * */
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
