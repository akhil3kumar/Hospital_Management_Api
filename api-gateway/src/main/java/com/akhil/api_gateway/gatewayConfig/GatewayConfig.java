package com.akhil.api_gateway.gatewayConfig;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("patient-service-route", r -> r.path("/patients/**")
                .filters(f -> f.addRequestHeader("X-Gateway-Request", "True"))
                .uri("lb://PATIENT-SERVICE"))
                .build();

    }
}
