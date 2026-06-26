package com.inn.common.dto.kafka;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentCreatedEvent {

    private Long appointmentId;
    private Long patientId;
    private String patientName;
    private String patientEmail;

    private Long doctorId;
    private String doctorName;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;
}
