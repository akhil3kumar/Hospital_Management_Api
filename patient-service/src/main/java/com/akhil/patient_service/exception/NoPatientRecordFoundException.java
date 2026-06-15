package com.akhil.patient_service.exception;

public class NoPatientRecordFoundException extends RuntimeException{
    public NoPatientRecordFoundException(String msg) {
        super(msg);
    }
}
