package com.akhil.doctor_service.service.impl;

import com.akhil.doctor_service.dto.AvailabilityRequest;
import com.akhil.doctor_service.dto.DoctorMapper;
import com.akhil.doctor_service.dto.DoctorRequest;
import com.akhil.doctor_service.entity.Doctor;
import com.akhil.doctor_service.repository.DoctorRepository;
import com.akhil.doctor_service.service.DoctorService;
import com.inn.common.dto.doctor_service.DoctorResponse;
import com.inn.common.enums.Specialization;
import com.inn.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Transactional
    @Override
    public DoctorResponse createDoctor(DoctorRequest request) {
        Doctor doctor = doctorMapper.toEntity(request);
        Doctor savedDoctor= doctorRepository.save(doctor);
        log.info("New Doctor added with ID: {}", savedDoctor.getId());
        return doctorMapper.toResponse(savedDoctor);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DoctorResponse> getAllDoctors() {

        List<Doctor> list= doctorRepository.findAll();
        return list.stream().map(doctorMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctor(Long doctorId) {
        Doctor doctor= findDoctorById(doctorId);
        return doctorMapper.toResponse(doctor);

    }

    @Transactional
    @Override
    public DoctorResponse updateDoctor(Long doctorId, DoctorRequest request) {
        Doctor doctor= findDoctorById(doctorId);
        doctor.setName(request.name());
        doctor.setEmail(request.email());
        doctor.setSpecialization(request.specialization());
        doctor.setPhoneNumber(request.phoneNumber());

        log.debug("Doctor entity updated: {}", doctor);
        Doctor updatedDoctor=doctorRepository.save(doctor);
        log.info("Doctor updated successfully with ID: {}", updatedDoctor.getId());
        return doctorMapper.toResponse(updatedDoctor);
    }

    @Transactional
    @Override
    public void deleteDoctor(Long doctorId) {
        Doctor doctor= findDoctorById(doctorId);
        doctorRepository.delete(doctor);
        log.info("Doctor deleted with ID: {}" ,doctor.getId());
    }



    private Doctor findDoctorById(Long doctorId) {
        log.info("Fetching doctor with ID: {}", doctorId);
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow(()->{
            log.error("No doctor record found with ID: {}", doctorId);
            return new ResourceNotFoundException("No doctor record found with ID: " + doctorId);
        });

        log.debug("Doctor entity retrieved: {}", doctor);
        return doctor;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DoctorResponse> getDoctorsBySpecialization(Specialization specialization) {
        List<Doctor> doctor=doctorRepository.findBySpecializationAndAvailableTrue(specialization);
        return doctor.stream().map(doctorMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<DoctorResponse> getDoctorsByAvailability() {
        List<Doctor> doctor=doctorRepository.findByAvailableTrue();
        return doctor.stream().map(doctorMapper::toResponse).toList();
    }

    @Transactional
    @Override
    public DoctorResponse updateDoctorAvailability(Long id, AvailabilityRequest availabilityRequest) {
        Doctor doctor = findDoctorById(id);
        log.info(
                "Updating availability of doctor {} to {}",
                id,
                availabilityRequest.available()
        );
        doctor.setAvailable(availabilityRequest.available());

        return doctorMapper.toResponse(doctor);
    }


}
