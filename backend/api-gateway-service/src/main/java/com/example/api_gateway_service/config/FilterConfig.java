package com.example.api_gateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

//Spring 컨텍스트 초기화 단계에서 가장 먼저 Bean 정의용으로 등록.
//@Configuration
public class FilterConfig {
    Environment env;

    public FilterConfig(Environment env) {
        this.env = env;
    }

//    @Bean
    public RouteLocator getRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/test-service/**")
                        .filters(f -> f.addRequestHeader("f-request", "test-request-header-by-java")
                                .addResponseHeader("f-response", "test-response-header-from-java"))
                        .uri("http://localhost:8081"))
//                .route(r -> r.path("/users/**")
//                        .filters(f -> f.addRequestHeader("f-request", "1st-request-header-by-java")
//                                .addResponseHeader("f-response", "1st-response-header-from-java"))
//                        .uri("http://localhost:60000"))
                .build();
    }



}
