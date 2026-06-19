package com.akhil.patient_service.service.impl;

import com.akhil.patient_service.dto.PatientMapper;
import com.inn.common.dto.patient_service.PatientRequest;
import com.inn.common.dto.patient_service.PatientResponse;
import com.akhil.patient_service.entity.Patient;
import com.akhil.patient_service.exception.NoPatientRecordFoundException;
import com.akhil.patient_service.repository.PatientRepository;
import com.akhil.patient_service.service.PatientService;
import com.inn.common.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Transactional
    @Override
    public PatientResponse addPatient(PatientRequest request) {

        if (patientRepository.existsByEmail(
                request.email())) {

            throw new ConflictException(
                    "Patient already exists with email: "
                            + request.email());
        }
        if (patientRepository.existsByPhoneNumber(
                request.phoneNumber())) {

            throw new ConflictException(
                    "Patient already exists with phone number: "
                            + request.phoneNumber());
        }

        Patient patient = patientMapper.toEntity(request);

        Patient savedPatient = patientRepository.save(patient);
        log.info("New patient added with ID: {}", savedPatient.getId());
        return patientMapper.toResponse(savedPatient);
    }

    @Override
    public List<PatientResponse> getAllPatients() {
         List<Patient> patientList=patientRepository.findByActiveTrue();
         return patientList.stream().map(patientMapper::toResponse).toList();
    }

    @Override
    public PatientResponse getPatient(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new NoPatientRecordFoundException(
                                "No patient record found with ID: "
                                        + patientId));

        if (!patient.getActive()) {
            throw new NoPatientRecordFoundException(
                    "Patient is inactive");
        }

        log.debug("Patient entity retrieved: {}", patient);
        return patientMapper.toResponse(patient);
    }

    @Transactional
    @Override
    public PatientResponse updatePatient(Long patientId,
                                         PatientRequest request) {

        Patient patient = findPatientById(patientId);

        if (patientRepository.existsByEmailAndIdNot(
                request.email(), patientId)) {

            throw new ConflictException(
                    "Patient already exists with email: "
                            + request.email());
        }
        if (patientRepository.existsByPhoneNumberAndIdNot(
                request.phoneNumber(), patientId)) {

            throw new ConflictException(
                    "Patient already exists with phone number: "
                            + request.phoneNumber());
        }

        patientMapper.updateEntity(patient, request);

        Patient updatedPatient = patientRepository.save(patient);

        log.info("Patient updated successfully with ID: {}",
                updatedPatient.getId());

        return patientMapper.toResponse(updatedPatient);
    }

    @Transactional
    @Override
    public void deactivePatient(Long patientId) {

        Patient patient=findPatientById(patientId);
        log.debug("Patient entity retrieved: {}", patient);
        patient.setActive(false);
        patientRepository.save(patient);
        log.info("Patient deactivated with ID: {}" ,patient.getId());
    }

    private Patient findPatientById(Long patientId) {
        log.info("Fetching patient with ID: {}", patientId);
        return  patientRepository.findById(patientId).orElseThrow(()->{
            log.error("No patient record found with ID: {}", patientId);
            return new NoPatientRecordFoundException("No patient record found with ID: " + patientId);
        });
    }
}
