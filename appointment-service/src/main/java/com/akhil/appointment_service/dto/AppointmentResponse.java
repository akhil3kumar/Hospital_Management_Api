package com.akhil.appointment_service.dto;


import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record AppointmentResponse(
        Long id,
        Long patientId,
        Long doctorId,
        LocalDateTime appointmentTime,
        String reason,
        String appointmentStatus
) { }
