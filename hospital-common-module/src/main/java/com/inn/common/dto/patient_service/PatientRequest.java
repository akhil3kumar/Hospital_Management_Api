package com.inn.common.dto.patient_service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PatientRequest(

        @NotBlank(message = "Name is required")
        String name,

        @Min(0)
        @Max(120)
        Integer age,

        @NotBlank(message = "Gender is required")
        String gender,

        @NotBlank(message = "Email is required")
        @Email
        String email,

        @NotBlank(message = "Phone Number is required")
        String phoneNumber,

        String address,

        String disease
) {
}
