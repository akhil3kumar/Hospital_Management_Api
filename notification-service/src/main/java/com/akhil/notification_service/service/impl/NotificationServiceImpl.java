package com.akhil.notification_service.service.impl;

import com.akhil.notification_service.entity.Notification;
import com.akhil.notification_service.repository.NotificationRepository;
import com.akhil.notification_service.service.NotificationService;
import com.inn.common.dto.kafka.AppointmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public void sendAppointmentConfirmation(AppointmentCreatedEvent event) {
        String message =
                "Hello "
                        + event.getPatientName()
                        + ", your appointment with Dr. "
                        + event.getDoctorName()
                        + " is confirmed for "
                        + event.getAppointmentDate();


        Notification notification =
                Notification.builder()
                        .appointmentId(
                                event.getAppointmentId())
                        .recipient(
                                event.getPatientEmail())
                        .subject(
                                "Appointment Confirmation")
                        .message(message)
                        .status("SENT")
                        .sentAt(LocalDateTime.now())
                        .build();

        notificationRepository.save(notification);

        log.info(
                "Notification saved for {}",
                event.getPatientEmail());
    }
}
