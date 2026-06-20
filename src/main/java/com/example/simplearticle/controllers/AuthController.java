package com.example.simplearticle.controllers;

import com.example.simplearticle.requests.AuthRequest;
import com.example.simplearticle.requests.UserRequest;
import com.example.simplearticle.response.ApiResponse;
import com.example.simplearticle.response.AuthResponse;
import com.example.simplearticle.response.UserResponse;
import com.example.simplearticle.services.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@SecurityRequirement(name = "ApiKeyAuth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody UserRequest payload) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "User register successfully",
                this.authService.register(payload)
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successfully",
                        authService.login(request)
                )
        );
    }
}
