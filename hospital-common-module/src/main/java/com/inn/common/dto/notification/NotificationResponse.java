package com.inn.common.dto.notification;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record NotificationResponse(

        Long id,

        Long appointmentId,

        String recipient,

        String subject,

        String message,

        String status,

        LocalDateTime sentAt

) {}