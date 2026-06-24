package com.akhil.appointment_service.repository;

import com.akhil.appointment_service.entity.Appointment;
import com.akhil.appointment_service.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    boolean existsByDoctorIdAndAppointmentTime(Long aLong, LocalDateTime localDateTime);

    boolean existsByDoctorIdAndAppointmentTimeAndIdNot(Long aLong, LocalDateTime localDateTime, Long appointmentId);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByAppointmentStatus(AppointmentStatus status);
}
