package com.akhil.doctor_service.service;

import com.inn.common.dto.doctor_service.AvailabilityResponse;
import com.inn.common.dto.doctor_service.DoctorScheduleRequest;
import com.inn.common.dto.doctor_service.DoctorScheduleResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface DoctorScheduleService {
    DoctorScheduleResponse createDoctorSchedule(Long doctorId, DoctorScheduleRequest request);

    List<DoctorScheduleResponse> getSchedulesByDoctorId(Long doctorId);

    AvailabilityResponse checkAvailability(Long doctorId, LocalDateTime appointmentTime);
}
