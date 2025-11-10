package com.example.user_service.service;

import com.example.user_service.entity.UserEntity;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.exception.DuplicateEmailException;
import com.example.user_service.exception.UserNotFoundException;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    @Override
    public UserResponse registerUser(RegisterRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new DuplicateEmailException("이미 등록된 이메일입니다: " + request.getEmail());
        });

        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .point(0)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        return UserResponse.fromEntity(user);
    }

    @Override
    public String loginUser(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 기본 role을 USER로 넣기
        return tokenProvider.createToken(user.getEmail(), "USER");
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponse::fromEntity).toList();
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserResponse::fromEntity)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public void addPoint(String email, int amount) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        user.setPoint(user.getPoint() + amount);
        userRepository.save(user);
    }
}
