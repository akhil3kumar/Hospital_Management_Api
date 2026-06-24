package com.akhil.appointment_service.kafka;

import com.inn.common.dto.kafka.AppointmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishAppointmentCreated(AppointmentCreatedEvent event) {

        kafkaTemplate.send("appointment-created-topic", event);
        log.info("Published AppointmentCreatedEvent: {}",event.getAppointmentId());
    }
}
