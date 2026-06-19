package com.akhil.doctor_service.dto;

import com.akhil.doctor_service.entity.Doctor;
import com.inn.common.dto.doctor_service.DoctorResponse;
import org.springframework.stereotype.Component;


@Component
public class DoctorMapper {

    public Doctor toEntity(DoctorRequest request){
        return Doctor.builder()
                .name(request.name())
                .specialization(request.specialization())
                .email(request.email())
                .phoneNumber((request.phoneNumber()))
                .build();
    }

    public DoctorResponse toResponse(Doctor doctor){
        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specialization(doctor.getSpecialization())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .available(doctor.getAvailable())
                .build();
    }
}
