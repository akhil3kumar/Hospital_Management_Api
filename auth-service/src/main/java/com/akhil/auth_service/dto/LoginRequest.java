package com.akhil.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest (
        @Email(message = "Email must be valid")
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
        String password)
{}
