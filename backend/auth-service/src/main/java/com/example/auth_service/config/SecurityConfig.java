package com.example.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 람다식으로 CSRF 비활성화
                .csrf(CsrfConfigurer::disable)
                // 요청 경로별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/refresh").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
