package com.akhil.appointment_service.service.impl;

import com.akhil.appointment_service.dto.AppointmentMapper;
import com.akhil.appointment_service.dto.AppointmentRequest;
import com.akhil.appointment_service.dto.AppointmentResponse;
import com.akhil.appointment_service.entity.Appointment;
import com.akhil.appointment_service.entity.AppointmentStatus;
import com.akhil.appointment_service.feignClient.DoctorFeignClient;
import com.akhil.appointment_service.feignClient.PatientFeignClient;
import com.akhil.appointment_service.kafka.AppointmentCreatedEvent;
import com.akhil.appointment_service.kafka.AppointmentEventProducer;
import com.akhil.appointment_service.repository.AppointmentRepository;
import com.akhil.appointment_service.service.AppointmentService;
import com.inn.common.dto.doctor_service.DoctorResponse;
import com.inn.common.dto.PatientResponse;
import com.inn.common.exception.BusinessValidationException;
import com.inn.common.exception.ConflictException;
import com.inn.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorFeignClient doctorFeignClient;
    private final PatientFeignClient patientFeignClient;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentEventProducer producer;
    @Override
    public AppointmentResponse createAppointment(AppointmentRequest request) {

        validateAppointment(request.patientId(), request.doctorId(), request.appointmentTime());

        if (appointmentRepository.existsByDoctorIdAndAppointmentTime(
                request.doctorId(),
                request.appointmentTime())) {

            throw new ConflictException(
                    "Appointment slot already booked");
        }

        Appointment savedAppointment = appointmentRepository.save(appointmentMapper.toEntity(request));
        log.info("Created Appointment with id: " + savedAppointment.getId());

        // add the appointment in kafka event
        AppointmentCreatedEvent event =
                AppointmentCreatedEvent.builder()
                        .appointmentId(savedAppointment.getId())
                        .patientId(savedAppointment.getPatientId())
                        .doctorId(savedAppointment.getDoctorId())
                        .appointmentTime(
                                savedAppointment.getAppointmentDateTime()
                                        .toString())
                        .build();

        producer.publishAppointmentCreated(event);
        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    public List<AppointmentResponse> getAllAppointment() {
        List<Appointment> appointmentList = appointmentRepository.findAll();

        return appointmentList.stream().map(appointmentMapper::toResponse).toList();
    }

    @Override
    public AppointmentResponse getSpecificAppointment(Long appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        return appointmentMapper.toResponse(appointment);
    }

    @Transactional
    @Override
    public AppointmentResponse updateAppointment(Long appointmentId, AppointmentRequest request) {
        Appointment appointment = findAppointmentById(appointmentId);
        if (appointment.getAppointmentStatus()
                == AppointmentStatus.CANCELLED) {

            throw new BusinessValidationException(
                    "Cancelled appointment cannot be modified");
        }
        validateAppointment(request.patientId(), request.doctorId(), request.appointmentTime());
        if (appointmentRepository.existsByDoctorIdAndAppointmentTimeAndIdNot(request.doctorId(),
                request.appointmentTime(),appointmentId) ) {
            throw new ConflictException("Appointment slot already booked");
        }

        appointment.setDoctorId(request.doctorId());
        appointment.setPatientId(request.patientId());

        appointment.setAppointmentDateTime(request.appointmentTime());
        appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);
        appointment.setReason(request.reason());

        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(updatedAppointment);
    }

    @Override
    @Transactional
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        if (appointment.getAppointmentStatus()
                == AppointmentStatus.CANCELLED) {

            throw new BusinessValidationException(
                    "Appointment already cancelled");
        }


        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    @Override
    public List<AppointmentResponse> getAllAppointmentByPatientId(Long patientId) {
        List<Appointment> appointmentList = appointmentRepository.findByPatientId(patientId);

        return appointmentList.stream().map(appointmentMapper::toResponse).toList();
    }

    @Override
    public List<AppointmentResponse> getAllAppointmentByDoctorId(Long doctorId) {
        List<Appointment> appointmentList =  appointmentRepository.findByDoctorId(doctorId);
        return appointmentList.stream().map(appointmentMapper::toResponse).toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentByStatus(AppointmentStatus status) {
        List<Appointment> list=appointmentRepository.findByStatus(status);

        return list.stream().map(appointmentMapper::toResponse).toList();
    }

    private void validateAppointment(Long patientId, Long doctorId, LocalDateTime appointmentTime) {
        log.debug("Validating appointment for patient {} and doctor {}",
                patientId,
                doctorId);
        PatientResponse patientResponse = patientFeignClient.getPatientById(patientId);

        if ( patientResponse== null) {
            throw new ResourceNotFoundException("Patient Not Found with id: " + patientId);
        }

        DoctorResponse doctorResponse = doctorFeignClient.findDoctorById(doctorId);
        if (doctorResponse == null) {
            throw new ResourceNotFoundException("Doctor Not Found with id: " + doctorId);
        }

        if(!doctorResponse.available()){
            throw new BusinessValidationException("Doctor is currently unavailable");
        }

        if (appointmentTime.isBefore(LocalDateTime.now())) {
            throw new BusinessValidationException(
                    "Appointment time cannot be in the past");
        }

        if(!doctorFeignClient.checkAvailabilityForSlot(doctorId, appointmentTime).isAvailable()){
            throw new ConflictException("Doctor Availability For Slot Not Available");
        }
    }

    private Appointment findAppointmentById(Long appointmentId) {

        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment Not Found with id: "
                                        + appointmentId));
    }


    }
