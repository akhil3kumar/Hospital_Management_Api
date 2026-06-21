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
                // Patient Service
                .route("patient-service-route", r -> r.path("/patients/**")
                        .filters(f -> f.addRequestHeader("X-Gateway-Request", "True"))
                        .uri("lb://PATIENT-SERVICE"))

                // Doctor Service
                .route("doctor-service-route", r -> r.path("/doctors/**")
                        .filters(f -> f.addRequestHeader("X-Gateway-Request", "True"))
                        .uri("lb://DOCTOR-SERVICE"))

                // Auth-service
                .route("auth-service-route", r -> r.path("/auth/**")
                        .filters(f -> f.addRequestHeader("X-Gateway-Request", "True"))
                        .uri("lb://AUTH-SERVICE"))

                // Any other microservice (add more as needed)
                .route("appointment-service-route", r -> r.path("/appointments/**")
                        .filters(f -> f.addRequestHeader("X-Gateway-Request", "True"))
                        .uri("lb://APPOINTMENT-SERVICE"))

                .build();
    }
}
