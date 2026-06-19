package com.akhil.doctor_service.service.impl;

import com.akhil.doctor_service.dto.DoctorScheduleMapper;
import com.akhil.doctor_service.entity.DoctorSchedule;
import com.akhil.doctor_service.repository.DoctorScheduleRepository;
import com.akhil.doctor_service.service.DoctorScheduleService;
import com.akhil.doctor_service.service.DoctorService;
import com.inn.common.dto.doctor_service.AvailabilityResponse;
import com.inn.common.dto.doctor_service.DoctorScheduleRequest;
import com.inn.common.dto.doctor_service.DoctorScheduleResponse;
import com.inn.common.exception.BusinessValidationException;
import com.inn.common.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorScheduleImpl implements DoctorScheduleService {
    private final DoctorService doctorService;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorScheduleMapper doctorScheduleMapper;
    @Override
    public DoctorScheduleResponse createDoctorSchedule(Long doctorId, DoctorScheduleRequest request) {
        doctorService.getDoctor(doctorId);
        if(!request.endTime().isAfter(request.startTime())){
            throw new BusinessValidationException("End time should be greater than start time");
        }

        if(doctorScheduleRepository.existsByDoctorIdAndDayOfWeek(doctorId,request.dayOfWeek())){
            throw new DuplicateResourceException(
                    "Schedule already exists for "
                            + request.dayOfWeek());
        }

        DoctorSchedule doctorSchedule= doctorScheduleMapper.toEntity(doctorId,request);

        DoctorSchedule savedDoctorSchedule=doctorScheduleRepository.save(doctorSchedule);

        return doctorScheduleMapper.toResponse(savedDoctorSchedule);


    }

    @Override
    public List<DoctorScheduleResponse> getSchedulesByDoctorId(Long doctorId) {
        doctorService.getDoctor(doctorId);

        List<DoctorSchedule> doctorScheduleList =doctorScheduleRepository.findByDoctorId(doctorId);

        return doctorScheduleList.stream().map(doctorScheduleMapper::toResponse).toList();
    }

    @Override
    public AvailabilityResponse checkAvailability(Long doctorId, LocalDateTime appointmentTime) {
        doctorService.getDoctor(doctorId);

        DoctorSchedule doctorScheduleByWeek = doctorScheduleRepository.findByDoctorIdAndDayOfWeek
                (doctorId,appointmentTime.getDayOfWeek()).orElseThrow(
                        () -> new BusinessValidationException(
                                "Doctor is not available on "+ appointmentTime.getDayOfWeek())
        );

        LocalTime appointmentLocalTime = appointmentTime.toLocalTime();

        if(appointmentLocalTime.isBefore(doctorScheduleByWeek.getStartTime()) ||
                appointmentLocalTime.isAfter(doctorScheduleByWeek.getEndTime())){
            return new AvailabilityResponse(false);
        }

        return new AvailabilityResponse(true);

    }
}
