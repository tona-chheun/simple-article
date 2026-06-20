package com.example.simplearticle.requests;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank String content
) {
}
