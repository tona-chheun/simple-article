package com.example.simplearticle.services;

import com.example.simplearticle.requests.AuthRequest;
import com.example.simplearticle.requests.UserRequest;
import com.example.simplearticle.response.AuthResponse;
import com.example.simplearticle.response.UserResponse;

public interface AuthService {
    UserResponse register(UserRequest payload);
    AuthResponse login(AuthRequest request);
}
