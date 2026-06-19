package com.akhil.appointment_service.dto;

import com.akhil.appointment_service.entity.Appointment;
import com.akhil.appointment_service.entity.AppointmentStatus;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {


    public Appointment toEntity(AppointmentRequest request) {
        return Appointment.builder()
                .doctorId(request.doctorId())
                .patientId(request.patientId())
                .appointmentStatus(AppointmentStatus.SCHEDULED)
                .appointmentDateTime(request.appointmentTime())
                .reason(request.reason())
                .build();
    }


    public AppointmentResponse toResponse(Appointment savedAppointment) {
        return AppointmentResponse.builder()
                .id(savedAppointment.getId())
                .doctorId(savedAppointment.getDoctorId())
                .patientId(savedAppointment.getPatientId())
                .appointmentTime(savedAppointment.getAppointmentDateTime())
                .reason(savedAppointment.getReason())
                .appointmentStatus(savedAppointment.getAppointmentStatus().toString())
                .build();

    }
}
