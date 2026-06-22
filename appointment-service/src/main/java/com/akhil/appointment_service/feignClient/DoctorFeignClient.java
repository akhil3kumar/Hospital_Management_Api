package com.akhil.appointment_service.feignClient;

import com.inn.common.dto.doctor_service.AvailabilityResponse;
import com.inn.common.dto.doctor_service.DoctorResponse;
import com.inn.common.dto.doctor_service.DoctorScheduleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@FeignClient(name = "doctor-service")
@RequestMapping("/doctors")
public interface DoctorFeignClient {

    @GetMapping("/{doctorId}")
    DoctorResponse findDoctorById(@PathVariable("doctorId") Long id);

    @GetMapping("/{doctorId}/schedules")
    DoctorScheduleResponse getSchedulesByDoctorId(@PathVariable("doctorId") Long doctorId);

    @GetMapping("/{doctorId}/availability")
    AvailabilityResponse checkAvailabilityForSlot(
            @PathVariable("doctorId") Long doctorId,
            @RequestParam("appointmentTime") LocalDateTime appointmentTime
    );
}
