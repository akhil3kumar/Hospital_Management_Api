package com.akhil.notification_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long appointmentId;

    private String recipient;

    private String subject;

    @Column(length = 1000)
    private String message;

    private String status;

    private LocalDateTime sentAt;
}
