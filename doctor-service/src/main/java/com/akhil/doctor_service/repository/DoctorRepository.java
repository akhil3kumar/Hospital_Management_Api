package com.akhil.doctor_service.repository;

import com.akhil.doctor_service.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByIdAndAvailableTrue(Long doctorId);

    List<Doctor> findByAvailableTrue();

    List<Doctor> findBySpecializationAndAvailableTrue(String specialization);
}
