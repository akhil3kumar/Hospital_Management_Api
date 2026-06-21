package com.akhil.auth_service.dto;

import lombok.Builder;

@Builder
public record RegisterResponse(
        String name,
        String email,
        String message
)
{}
