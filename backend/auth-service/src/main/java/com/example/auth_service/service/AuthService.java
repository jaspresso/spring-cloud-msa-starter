package com.example.auth_service.service;

import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.TokenResponse;
import com.example.auth_service.util.TokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final WebClient.Builder webClientBuilder; // user-service 호출용

    // 로그인
    public TokenResponse login(LoginRequest request) {
        // 1️⃣ user-service 호출로 사용자 검증
        Boolean isValid = webClientBuilder.build()
                .post()
                .uri("http://USER-SERVICE/users/login-check")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block(); // WebFlux 사용 시 block()은 테스트용. 실무는 Mono 반환

        if (isValid == null || !isValid) {
            throw new RuntimeException("Invalid email or password");
        }

        // 2️⃣ TokenProvider에서 토큰 생성
        String userId = request.getEmail(); // 예: email을 userId로 사용
        String accessToken = tokenProvider.createToken(userId, request.getEmail());
        String refreshToken = tokenProvider.createRefreshToken(userId);

        return new TokenResponse(accessToken, refreshToken);
    }

    // Refresh Token 갱신
    public TokenResponse refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        Claims claims = tokenProvider.getClaims(refreshToken);
        String userId = claims.getSubject();
        String email = claims.get("email", String.class);

        String newAccessToken = tokenProvider.createToken(userId, email);

        return new TokenResponse(newAccessToken, refreshToken);
    }
}