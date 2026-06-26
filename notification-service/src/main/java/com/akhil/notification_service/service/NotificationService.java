package com.akhil.notification_service.service;

import com.inn.common.dto.kafka.AppointmentCreatedEvent;
import com.inn.common.dto.notification.NotificationResponse;

import java.util.List;

public interface NotificationService {
    public void sendAppointmentConfirmation(AppointmentCreatedEvent event);

    List<NotificationResponse> getAllNotifications();

    NotificationResponse getSpecificNotification(Long id);

    List<NotificationResponse> getNotificationsByAppointment(Long appointmentId);
}
