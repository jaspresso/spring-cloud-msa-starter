package com.example.test_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestServiceController {

    @GetMapping("/test")
    public String test() {
        return "Test Service Running with Eureka (Spring Boot 3.5 + Cloud 2025)";
    }
}
