package com.akhil.patient_service.dto;

import com.akhil.patient_service.entity.Patient;
import com.inn.common.dto.patient_service.PatientRequest;
import com.inn.common.dto.patient_service.PatientResponse;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequest request){
        return Patient.builder()
                .name(request.name())
                .age(request.age())
                .gender(request.gender())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .address(request.address())
                .disease(request.disease())
                .active(true)
                .build();
    }

    public PatientResponse toResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getEmail(),
                patient.getPhoneNumber(),
                patient.getAddress(),
                patient.getDisease(),
                patient.getActive(),
                patient.getCreatedAt(),
                patient.getUpdatedAt()
        );
    }

    public void updateEntity(Patient patient, PatientRequest request) {

        patient.setName(request.name());
        patient.setAge(request.age());
        patient.setGender(request.gender());
        patient.setEmail(request.email());
        patient.setPhoneNumber(request.phoneNumber());
        patient.setAddress(request.address());
        patient.setDisease(request.disease());
    }

}
