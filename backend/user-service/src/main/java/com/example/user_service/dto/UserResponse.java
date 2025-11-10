package com.example.user_service.dto;

import com.example.user_service.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String email;
    private String name;
    private int point;
    private LocalDateTime createdAt;

    public static UserResponse fromEntity(UserEntity user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .point(user.getPoint())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
