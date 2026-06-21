package com.akhil.auth_service.exception;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException(String msg) {
        super(msg);
    }
}
