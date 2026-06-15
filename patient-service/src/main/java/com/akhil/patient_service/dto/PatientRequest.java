package com.akhil.patient_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PatientRequest (
        @NotBlank(message = "Name is mandatory")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,

        @NotNull(message = "Age is required")
        @Min(value = 0, message = "Age must be non-negative")
        int age,

        @NotBlank(message = "Disease is mandatory")
        @Size(min = 3, max = 100, message = "Disease must be between 3 and 100 characters")
        String disease
){}
