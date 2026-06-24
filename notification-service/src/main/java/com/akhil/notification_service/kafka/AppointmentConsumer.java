package com.akhil.notification_service.kafka;

import com.akhil.notification_service.service.NotificationService;
import com.inn.common.dto.kafka.AppointmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppointmentConsumer {
    private final NotificationService notificationService;

    @KafkaListener(
            topics = "appointment-created",
            groupId = "notification-group"
    )
    public void consume(AppointmentCreatedEvent event) {
        log.info("Received Event : {}", event);

        log.info(
                "Received appointment event: {}",
                event.getAppointmentId());

        notificationService
                .sendAppointmentConfirmation(event);
    }
}
