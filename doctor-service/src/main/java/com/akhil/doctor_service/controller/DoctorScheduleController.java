package com.akhil.doctor_service.controller;

import com.akhil.doctor_service.service.DoctorScheduleService;
import com.inn.common.dto.doctor_service.AvailabilityResponse;
import com.inn.common.dto.doctor_service.DoctorScheduleRequest;
import com.inn.common.dto.doctor_service.DoctorScheduleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorScheduleController {

        private final DoctorScheduleService doctorScheduleService;

        @PostMapping("/{doctorId}/schedules")
        public ResponseEntity<DoctorScheduleResponse> createDoctorSchedule(@PathVariable("doctorId") Long doctorId,@RequestBody @Valid DoctorScheduleRequest request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(doctorScheduleService.createDoctorSchedule(doctorId,request));
        }

        @GetMapping("/{doctorId}/schedules")
        public ResponseEntity<List<DoctorScheduleResponse>> getSchedulesByDoctorId(@PathVariable Long doctorId) {
            return ResponseEntity.status(HttpStatus.OK).body(doctorScheduleService.getSchedulesByDoctorId(doctorId));
        }

        @GetMapping("/{doctorId}/availability")
        public ResponseEntity<AvailabilityResponse>  checkAvailabilityForSlot(@PathVariable Long doctorId, @RequestParam LocalDateTime appointmentTime) {
            return ResponseEntity.status(HttpStatus.OK).body(doctorScheduleService.checkAvailability(doctorId, appointmentTime));
        }


}
