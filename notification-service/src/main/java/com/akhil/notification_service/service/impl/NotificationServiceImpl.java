package com.akhil.notification_service.service.impl;

import com.akhil.notification_service.entity.Notification;
import com.akhil.notification_service.entity.NotificationStatus;
import com.akhil.notification_service.repository.NotificationRepository;
import com.akhil.notification_service.service.EmailService;
import com.akhil.notification_service.service.NotificationService;
import com.inn.common.dto.kafka.AppointmentCreatedEvent;
import com.inn.common.dto.notification.NotificationResponse;
import com.inn.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final EmailService emailService;
    private final NotificationRepository notificationRepository;

    @Override
    public void sendAppointmentConfirmation(AppointmentCreatedEvent event) {

        Notification notification = buildNotification(event);

        notificationRepository.save(notification);

        log.info("Sending appointment confirmation email to {}", event.getPatientEmail());

        processNotification(notification);

    }

    private Notification buildNotification(AppointmentCreatedEvent event) {

        String message = """
            Dear %s,

            Your appointment with Dr. %s has been confirmed.

            Date : %s
            Time : %s

            Thank you.
            """
                .formatted(
                        event.getPatientName(),
                        event.getDoctorName(),
                        event.getAppointmentDate(),
                        event.getAppointmentTime()
                );

        return Notification.builder()
                .appointmentId(event.getAppointmentId())
                .recipient(event.getPatientEmail())
                .subject("Appointment Confirmation")
                .message(message)
                .status(NotificationStatus.PENDING)
                .build();
    }

    private Notification processNotification(Notification notification) {

        try {

            emailService.sendAppointmentMail(
                    notification.getRecipient(),
                    notification.getSubject(),
                    notification.getMessage()
            );

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

            log.info("Email sent successfully to {}", notification.getRecipient());

        } catch (Exception e) {

            notification.setStatus(NotificationStatus.FAILED);

            log.error("Failed to send email to {}", notification.getRecipient(), e);
        }

        return notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponse> getAllNotifications() {
        List<Notification> list = notificationRepository.findAll();

        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .appointmentId(notification.getAppointmentId())
                .recipient(notification.getRecipient())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .status(notification.getStatus().toString())
                .sentAt(notification.getSentAt())
                .build();
    }

    @Override
    public NotificationResponse getSpecificNotification(Long id) {
       Notification notification = notificationRepository.findById(id)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Notification not found with the id: " + id));

       return toResponse(notification);
    }

    @Override
    public List<NotificationResponse> getNotificationsByAppointment(Long appointmentId) {
        List<Notification> list = notificationRepository.findByAppointmentId(appointmentId);

        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
