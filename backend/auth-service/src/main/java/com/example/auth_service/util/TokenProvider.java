package com.example.auth_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenProvider {

    // 🔑 JWT 서명용 비밀키 (256bit 이상 권장)
    private static final String SECRET = "MySuperSecretKeyForJwtMySuperSecretKeyForJwt";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 🔒 유효시간: 1시간
    private static final long EXPIRATION = 1000 * 60 * 60;

    // ✅ Access Token 생성, 짧은 유효기간, Claims에 email/role 포함
    public String createToken(String email, String role) {
        return Jwts.builder()
                .subject(email) // ✅ 최신 API에서는 setSubject 대신 subject()
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET_KEY)
                .compact();
    }

    // ✅ refresh token 생성 (유효기간 길게)
    public String createRefreshToken(String userId) {
        long refreshExpiration = 1000 * 60 * 60 * 24 * 7; // 7일
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(SECRET_KEY)
                .compact();
    }

    // ✅ 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()              // ⚠️ parser()는 여전히 사용 가능하지만 parserBuilder() 권장
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Claims 추출 메서드 (AuthService에서 사용하는 getClaims)
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ✅ 이메일(subject) 추출
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ 권한(role) 추출
    public String getRoleFromToken(String token) {
        return getClaims(token).get("role", String.class);
    }



}