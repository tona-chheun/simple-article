package com.example.simplearticle.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String name,
        @Email String email,
        @NotBlank String password
) {}
