package com.akhil.doctor_service.dto;

import com.inn.common.enums.Specialization;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record DoctorRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,

        @NotNull(message = "Specialization is required")
        Specialization specialization,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^[6-9][0-9]{9}$",
                message = "Phone number must be a valid Indian mobile number"
        )
        String phoneNumber
) {}