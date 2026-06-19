package com.inn.common.dto;

import lombok.Builder;

@Builder
public record PatientResponse(
        Long id,
        String name,
        int age,
        String disease
)
{}