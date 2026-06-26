package com.akhil.notification_service.controller;

import com.akhil.notification_service.entity.Notification;
import com.akhil.notification_service.service.NotificationService;
import com.inn.common.dto.notification.NotificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getAllNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getSpecificNotification(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getSpecificNotification(id));
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getNotificationsByAppointment(appointmentId));
    }
}
