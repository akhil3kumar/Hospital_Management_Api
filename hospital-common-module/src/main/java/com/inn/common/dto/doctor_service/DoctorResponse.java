package com.inn.common.dto.doctor_service;

import com.inn.common.enums.Specialization;
import lombok.Builder;

@Builder
public record DoctorResponse(
        Long id,
        String name,
        Specialization specialization,
        String email,
        String phoneNumber,
        Boolean available
) {}
