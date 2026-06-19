package com.inn.common.dto.doctor_service;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record DoctorScheduleRequest(

        @NotNull(message = "Day of week is required")
        DayOfWeek dayOfWeek,

        @NotNull(message = "Start time is required")
        LocalTime startTime,

        @NotNull(message = "End time is required")
        LocalTime endTime
){
}