package com.inn.common.dto.kafka;

import lombok.*;

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

    private String appointmentDate;
}
