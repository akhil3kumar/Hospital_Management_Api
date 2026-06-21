package com.akhil.auth_service.dto;

import lombok.Builder;

@Builder
public record AuthResponse (
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn
)
{}
