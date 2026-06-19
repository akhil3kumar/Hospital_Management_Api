package com.akhil.patient_service.service;



import com.inn.common.dto.patient_service.PatientRequest;
import com.inn.common.dto.patient_service.PatientResponse;

import java.util.List;

public interface PatientService {
    PatientResponse addPatient(PatientRequest request);

    List<PatientResponse> getAllPatients();

    PatientResponse getPatient(Long patientId);

    PatientResponse updatePatient(Long patientId, PatientRequest request);

    void deactivePatient(Long patientId);

}
