package com.akhil.doctor_service.controller;

import com.akhil.doctor_service.dto.AvailabilityRequest;
import com.akhil.doctor_service.dto.DoctorRequest;
import com.akhil.doctor_service.service.DoctorService;
import com.inn.common.dto.doctor_service.DoctorResponse;
import com.inn.common.enums.Specialization;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor(@RequestBody @Valid DoctorRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(request));
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        return  ResponseEntity.status(HttpStatus.OK).body(doctorService.getAllDoctors());
    }


    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> getDoctor(@PathVariable Long doctorId) {
        return  ResponseEntity.status(HttpStatus.OK).body(doctorService.getDoctor(doctorId));
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> updateDoctor(@PathVariable Long doctorId, @RequestBody @Valid DoctorRequest request) {
        return  ResponseEntity.status(HttpStatus.OK).body(doctorService.updateDoctor(doctorId,request));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<DoctorResponse>> getDoctorsBySpecialization
            (@PathVariable Specialization specialization) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(doctorService.getDoctorsBySpecialization(specialization));
    }

    @GetMapping("/available")
    public ResponseEntity<List<DoctorResponse>> getDoctorsByAvailability
            () {
        return ResponseEntity.status(HttpStatus.OK)
                .body(doctorService.getDoctorsByAvailability());
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<DoctorResponse> updateDoctorAvailability(@PathVariable Long id,
                                                           @RequestBody @Valid AvailabilityRequest availabilityRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(doctorService.updateDoctorAvailability(id,availabilityRequest));
    }
}
