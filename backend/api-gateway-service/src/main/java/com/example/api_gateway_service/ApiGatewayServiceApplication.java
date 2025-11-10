package com.example.api_gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableDiscoveryClient
//@ComponentScan(basePackages = {
//        "com.example.api_gateway_service",
//        "com.example.common" // 여기서 common 모듈 스캔
//})
public class ApiGatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayServiceApplication.class, args);
    }

}
