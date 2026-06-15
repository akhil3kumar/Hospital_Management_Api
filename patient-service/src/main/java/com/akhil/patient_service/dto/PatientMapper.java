package com.akhil.patient_service.dto;

import com.akhil.patient_service.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequest request){
        return Patient.builder()
                .name(request.name())
                .age(request.age())
                .disease(request.disease())
                .build();
    }

    public PatientResponse toResponse(Patient patient){
        return PatientResponse.builder()
                .id(patient.getId())
                .name(patient.getName())
                .age(patient.getAge())
                .disease(patient.getDisease())
                .build();
    }

}
