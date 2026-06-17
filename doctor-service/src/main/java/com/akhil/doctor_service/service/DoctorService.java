package com.akhil.doctor_service.service;

import com.akhil.doctor_service.dto.AvailabilityRequest;
import com.akhil.doctor_service.dto.DoctorRequest;
import com.akhil.doctor_service.dto.DoctorResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface DoctorService {
    DoctorResponse createDoctor(@Valid DoctorRequest request);

    List<DoctorResponse> getAllDoctors();

    DoctorResponse getDoctor(Long doctorId);

    DoctorResponse updateDoctor(Long doctorId, @Valid DoctorRequest request);

    void deleteDoctor(Long doctorId);

    List<DoctorResponse> getDoctorsBySpecialization(String specialization);

    List<DoctorResponse> getDoctorsByAvailablilty();

    DoctorResponse updateDoctorByIdAndAvailablity(Long id, AvailabilityRequest availabilityRequestrequest);
}
