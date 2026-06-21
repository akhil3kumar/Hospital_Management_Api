package com.akhil.auth_service.service;

import com.akhil.auth_service.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    RegisterResponse register(RegisterReq registerReq);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    String logout(String refreshToken);
}
