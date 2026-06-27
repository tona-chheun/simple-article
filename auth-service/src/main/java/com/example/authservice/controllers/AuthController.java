package com.example.authservice.controllers;

import com.example.authservice.request.LoginRequest;
import com.example.authservice.response.AuthResponse;
import com.example.authservice.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth management APIs")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Authentication")
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
