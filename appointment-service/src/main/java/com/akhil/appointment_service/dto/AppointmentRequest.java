package com.akhil.appointment_service.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequest(
        @NotNull(message = "Patient Id is required")
        Long patientId,

        @NotNull(message = "Doctor Id is required")
        Long doctorId,

        @NotNull(message = "Appointment time is required")
        LocalDateTime appointmentTime,

        String reason
) {}
