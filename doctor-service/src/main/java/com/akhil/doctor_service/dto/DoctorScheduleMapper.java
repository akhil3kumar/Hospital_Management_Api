package com.akhil.doctor_service.dto;

import com.akhil.doctor_service.entity.DoctorSchedule;
import com.inn.common.dto.doctor_service.DoctorScheduleRequest;
import com.inn.common.dto.doctor_service.DoctorScheduleResponse;
import org.springframework.stereotype.Component;

@Component
public class DoctorScheduleMapper {

    public DoctorSchedule toEntity(Long doctorId, DoctorScheduleRequest request) {
        return DoctorSchedule.builder()
                .doctorId(doctorId)
                .dayOfWeek(request.dayOfWeek())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .build();
    }

    public DoctorScheduleResponse toResponse(DoctorSchedule savedDoctorSchedule) {
        return DoctorScheduleResponse.builder()
                .dayOfWeek(savedDoctorSchedule.getDayOfWeek())
                .availableFrom(savedDoctorSchedule.getStartTime())
                .availableTo(savedDoctorSchedule.getEndTime())
                .build();
    }
}
