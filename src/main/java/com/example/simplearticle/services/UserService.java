package com.example.simplearticle.services;

import com.example.simplearticle.requests.UserRequest;
import com.example.simplearticle.requests.UserUpdateRequest;
import com.example.simplearticle.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDetails loadUserByUsername(String email);
    List<UserResponse> getAll();
    UserResponse findById(UUID id);
    UserResponse create(UserRequest payload);
    UserResponse update(UUID id, UserUpdateRequest payload);
    void delete(UUID id);
}
