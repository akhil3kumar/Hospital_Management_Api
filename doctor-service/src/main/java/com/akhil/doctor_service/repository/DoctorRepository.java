package com.akhil.doctor_service.repository;

import com.akhil.doctor_service.entity.Doctor;
import com.inn.common.enums.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByAvailableTrue();

    List<Doctor> findBySpecializationAndAvailableTrue(Specialization specialization);
}
