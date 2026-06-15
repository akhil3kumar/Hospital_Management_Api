package com.akhil.patient_service.service.impl;

import com.akhil.patient_service.dto.PatientMapper;
import com.akhil.patient_service.dto.PatientRequest;
import com.akhil.patient_service.dto.PatientResponse;
import com.akhil.patient_service.entity.Patient;
import com.akhil.patient_service.exception.NoPatientRecordFoundException;
import com.akhil.patient_service.repository.PatientRepository;
import com.akhil.patient_service.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    @Override
    public PatientResponse addPatient(PatientRequest request) {
        Patient patient = patientMapper.toEntity(request);
        Patient savedPatient = patientRepository.save(patient);
        log.info("New patient added with ID: {}", savedPatient.getId());
        return patientMapper.toResponse(savedPatient);
    }

    @Override
    public List<PatientResponse> getAllPatients() {
         List<Patient> patientList=patientRepository.findAll();
         return patientList.stream().map(patientMapper::toResponse).toList();
    }

    @Override
    public PatientResponse getPatient(Long patientId) {

        Patient patient=findPatientById(patientId);
        log.debug("Patient entity retrieved: {}", patient);
        return patientMapper.toResponse(patient);
    }

    @Override
    public PatientResponse updatePatient(Long patientId, PatientRequest request) {
        Patient patient=findPatientById(patientId);

        log.debug("Patient entity updated: {}", patient);
        patient.setName(request.name());
        patient.setAge(request.age());
        patient.setDisease(request.disease());
        Patient updatedPatient=patientRepository.save(patient);
        log.info("Patient updated successfully with ID: {}", updatedPatient.getId());
        return patientMapper.toResponse(updatedPatient);

    }

    @Override
    public void deletePatient(Long patientId) {

        Patient patient=findPatientById(patientId);
        log.debug("Patient entity retrieved: {}", patient);
        patientRepository.delete(patient);
        log.info("Patient deleted with ID: {}" ,patient.getId());
    }

    private Patient findPatientById(Long patientId) {
        log.info("Fetching patient with ID: {}", patientId);
        return  patientRepository.findById(patientId).orElseThrow(()->{
            log.error("No patient record found with ID: {}", patientId);
            return new NoPatientRecordFoundException("No patient record found with ID: " + patientId);
        });
    }
}
