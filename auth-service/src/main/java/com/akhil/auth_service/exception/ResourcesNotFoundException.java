package com.akhil.auth_service.exception;

public class ResourcesNotFoundException extends RuntimeException {
    public ResourcesNotFoundException(String msg) {
        super(msg);
    }
}
