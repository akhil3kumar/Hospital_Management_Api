package com.akhil.patient_service.dto;

import lombok.Builder;

@Builder
public record PatientResponse(
        Long id,
        String name,
        int age,
        String disease
)
{}
