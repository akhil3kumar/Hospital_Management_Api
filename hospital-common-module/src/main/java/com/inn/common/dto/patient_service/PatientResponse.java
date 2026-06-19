package com.inn.common.dto.patient_service;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PatientResponse(

        Long id,
        String name,
        Integer age,
        String gender,
        String email,
        String phoneNumber,
        String address,
        String disease,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}




