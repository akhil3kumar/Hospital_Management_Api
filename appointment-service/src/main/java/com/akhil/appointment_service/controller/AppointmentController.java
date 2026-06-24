package com.akhil.appointment_service.controller;

import com.akhil.appointment_service.dto.AppointmentRequest;
import com.akhil.appointment_service.dto.AppointmentResponse;
import com.akhil.appointment_service.entity.AppointmentStatus;
import com.akhil.appointment_service.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody @Valid AppointmentRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(request));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointment(){
        return ResponseEntity.ok(appointmentService.getAllAppointment());
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> getSpecificAppointment(@PathVariable Long appointmentId) {
        return  ResponseEntity.ok(appointmentService.getSpecificAppointment(appointmentId));
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> updateAppointment(
            @PathVariable Long appointmentId, @RequestBody @Valid AppointmentRequest request) {
        return  ResponseEntity.ok(appointmentService.updateAppointment(appointmentId,request));
    }

    @PatchMapping("/{appointmentId}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointmentByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAllAppointmentByPatientId(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointmentByDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAllAppointmentByDoctorId(doctorId));
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentByStatus(@PathVariable AppointmentStatus status){
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAppointmentByStatus(status));
    }


}
