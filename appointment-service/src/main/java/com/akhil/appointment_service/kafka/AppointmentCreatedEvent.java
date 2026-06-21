package com.akhil.appointment_service.kafka;

import lombok.Builder;

@Builder
public record AppointmentCreatedEvent(
        Long appointmentId,
        Long patientId,
        Long doctorId,
        String appointmentTime
) {
}