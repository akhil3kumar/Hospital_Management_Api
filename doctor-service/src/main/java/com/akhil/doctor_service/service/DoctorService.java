package com.akhil.doctor_service.service;

import com.akhil.doctor_service.dto.AvailabilityRequest;
import com.akhil.doctor_service.dto.DoctorRequest;
import com.inn.common.dto.doctor_service.DoctorResponse;
import com.inn.common.enums.Specialization;
import jakarta.validation.Valid;

import java.util.List;

public interface DoctorService {
    DoctorResponse createDoctor(@Valid DoctorRequest request);

    List<DoctorResponse> getAllDoctors();

    DoctorResponse getDoctor(Long doctorId);

    DoctorResponse updateDoctor(Long doctorId, @Valid DoctorRequest request);

    void deleteDoctor(Long doctorId);

    List<DoctorResponse> getDoctorsBySpecialization(Specialization specialization);

    List<DoctorResponse> getDoctorsByAvailability();

    DoctorResponse updateDoctorAvailability(Long id, AvailabilityRequest availabilityRequestrequest);
}
