package com.example.auth_service.controller;

import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.TokenResponse;
import com.example.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokens = authService.login(loginRequest);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestHeader("Authorization") String refreshToken) {
        TokenResponse newTokens = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(newTokens);
    }
}
