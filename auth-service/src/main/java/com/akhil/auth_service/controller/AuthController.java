package com.akhil.auth_service.controller;

import com.akhil.auth_service.dto.*;
import com.akhil.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse>  register(@Valid @RequestBody
                                                      RegisterReq registerReq) {
        RegisterResponse registerResponse = authService.register(registerReq);
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse authResponse = authService.refreshToken(request);
        return ResponseEntity.ok(authResponse);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(String refreshToken){
        return ResponseEntity.ok().body(authService.logout(refreshToken));
    }

    @GetMapping("/test")
    public String testauth(){
        return "auth service working properly for ";
    }
}



