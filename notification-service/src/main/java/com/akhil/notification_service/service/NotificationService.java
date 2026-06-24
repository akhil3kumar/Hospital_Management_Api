package com.akhil.notification_service.service;

import com.inn.common.dto.kafka.AppointmentCreatedEvent;

public interface NotificationService {
    public void sendAppointmentConfirmation(AppointmentCreatedEvent event);
}
