package com.example.user_service.config;

import com.example.user_service.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public WebSecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    // ✅ PasswordEncoder Bean 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ✅ CSRF 비활성화 (POST 테스트용)
                .csrf(csrf -> csrf.disable())

                // ✅ 요청별 접근 허용 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/users/register",
                                "/users/login",
                                "/h2-console/**",
                                "/actuator/**"
                        ).permitAll()
                        .requestMatchers("/users").hasRole("USER")  // 이제 USER 권한 체크 가능
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)

                // ✅ H2 콘솔 프레임 허용
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // ✅ 폼 로그인, HTTP Basic 모두 비활성화
                .formLogin(login -> login.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}