package com.akhil.notification_service.service;

import java.time.LocalDate;
import java.time.LocalTime;

public interface EmailService {

    void sendAppointmentMail(String to, String subject, String message);

}
