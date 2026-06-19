package com.akhil.patient_service.repository;

import com.akhil.patient_service.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByPhoneNumberAndIdNot(String phoneNo, Long id);
    boolean existsByEmailAndIdNot(String email , Long id);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    List<Patient> findByActiveTrue();
}
