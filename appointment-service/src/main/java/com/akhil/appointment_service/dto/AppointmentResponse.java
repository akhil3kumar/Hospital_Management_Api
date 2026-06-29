package com.akhil.appointment_service.dto;


import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record AppointmentResponse(
        Long id,
        Long patientId,
        String patientName,
        Long doctorId,
        String doctorName,
        LocalDateTime appointmentTime,
        String reason,
        String appointmentStatus
) { }
