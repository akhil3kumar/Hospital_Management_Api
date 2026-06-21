package com.akhil.auth_service.dto;

import com.netflix.spectator.impl.PatternExpr;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterReq (
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 15, message = "Password must be between 6 and 15 characters")
        String password
)
{}
