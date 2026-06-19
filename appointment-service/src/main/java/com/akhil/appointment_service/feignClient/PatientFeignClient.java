package com.akhil.appointment_service.feignClient;

import com.inn.common.dto.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service")
public interface PatientFeignClient {

    @GetMapping("/patients/{patientsId}")
    PatientResponse getPatientById(@PathVariable("patientsId") Long id);
}
