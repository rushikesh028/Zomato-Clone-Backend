package com.tastybuddy.tastybuddy_backend.dto;

public record AuthResponse(
        String token,
        String tokenType,
        long expiresInMs,
        UserResponse user
) {
}
