package com.example.user_service.service;

import com.example.user_service.dto.*;

import java.util.List;

public interface UserService {

    UserResponse registerUser(RegisterRequest request);
    String loginUser(LoginRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserByEmail(String email);
    void addPoint(String email, int amount);
}
