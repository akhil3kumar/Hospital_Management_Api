package com.akhil.patient_service.service;


import com.akhil.patient_service.dto.PatientRequest;
import com.akhil.patient_service.dto.PatientResponse;
import com.akhil.patient_service.entity.Patient;

import java.util.List;

public interface PatientService {
    PatientResponse addPatient(PatientRequest request);

    List<PatientResponse> getAllPatients();

    PatientResponse getPatient(Long patientId);

    PatientResponse updatePatient(Long patientId, PatientRequest request);

    void deletePatient(Long patientId);
}
