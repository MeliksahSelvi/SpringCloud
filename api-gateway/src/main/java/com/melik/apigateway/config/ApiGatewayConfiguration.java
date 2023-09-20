package com.melik.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author mselvi
 * @Created 17.09.2023
 */

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                /*
                * get ile başlayan bir uri geldiğinde onu http://httpbin.org:80 adresine yönlendirir.
                * api gateway üzerinden currency exchange servisindeki retrieveExchangeValue endpointine ulaşırken uri çok karmaşık hale geliyor.
                * http://localhost:8765/currency-exchange/currency-exchange/from/USD/to/INR mesela bu uri duplicate ifade var.
                * currency-exchange ile başlayan bir uri geldiğinde onu service discovery'deki kayıtlı olduğu isme yönlendiriyoruz.
                *
                *
                * currency-conversion/new diye fake url gelse onu geldiği şekliyle currency-conversion'a yönlendiriyoruz.
                * */
                .route(p -> p.path("/get")
                        .filters(f -> f.addRequestHeader("MyHeader", "MyURI")
                                .addRequestParameter("Param", "MyParam"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion/${segment}"))
                        .uri("lb://currency-conversion"))
                .build();
    }
}
