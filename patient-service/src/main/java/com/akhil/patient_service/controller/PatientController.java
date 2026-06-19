package com.akhil.patient_service.controller;

import com.inn.common.dto.patient_service.PatientRequest;
import com.inn.common.dto.patient_service.PatientResponse;
import com.akhil.patient_service.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@Slf4j
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponse> addPatient(@RequestBody @Valid PatientRequest request) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(patientService.addPatient(request));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        return  ResponseEntity.status(HttpStatus.OK).body(patientService.getAllPatients());
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable Long patientId) {
        return  ResponseEntity.status(HttpStatus.OK).body(patientService.getPatient(patientId));
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long patientId, @RequestBody @Valid PatientRequest request) {
        return  ResponseEntity.status(HttpStatus.OK).body(patientService.updatePatient(patientId,request));
    }

    @PatchMapping("/{patientId}")
    public ResponseEntity<Void> deactivePatient(@PathVariable Long patientId) {
        patientService.deactivePatient(patientId);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}










