package com.inn.common.dto;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp
) {
}