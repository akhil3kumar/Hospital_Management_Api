package com.akhil.doctor_service.dto;

import lombok.Builder;

@Builder
public record DoctorResponse(
        Long id,
        String name,
        String specialization,
        String email,
        String phoneNumber,
        Boolean available
) {}
