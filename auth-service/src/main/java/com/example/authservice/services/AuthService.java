package com.example.authservice.services;

import com.example.authservice.request.LoginRequest;
import com.example.authservice.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}
