package com.akhil.doctor_service.dto;

import jakarta.validation.constraints.NotNull;

public record AvailabilityRequest(
        @NotNull(message = "Availability status is required")
        Boolean available
) {}
