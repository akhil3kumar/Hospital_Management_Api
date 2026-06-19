package com.akhil.appointment_service.service;

import com.akhil.appointment_service.dto.AppointmentRequest;
import com.akhil.appointment_service.dto.AppointmentResponse;
import com.akhil.appointment_service.entity.AppointmentStatus;
import jakarta.validation.Valid;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse createAppointment(AppointmentRequest request);

    List<AppointmentResponse> getAllAppointment();

    AppointmentResponse getSpecificAppointment(Long appointmentId);

    AppointmentResponse updateAppointment(Long appointmentId, @Valid AppointmentRequest request);

    void cancelAppointment(Long appointmentId);

    List<AppointmentResponse> getAllAppointmentByPatientId(Long patientId);

    List<AppointmentResponse> getAllAppointmentByDoctorId(Long doctorId);

    List<AppointmentResponse> getAppointmentByStatus(AppointmentStatus status);
}
