package com.akhil.doctor_service.exception;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException(String msg) {
        super(msg);
    }
}
