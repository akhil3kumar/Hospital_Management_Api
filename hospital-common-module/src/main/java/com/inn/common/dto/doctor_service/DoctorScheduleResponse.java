package com.inn.common.dto.doctor_service;

import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Builder
public record DoctorScheduleResponse(
        DayOfWeek dayOfWeek,
        LocalTime availableFrom,
        LocalTime availableTo
) {}
