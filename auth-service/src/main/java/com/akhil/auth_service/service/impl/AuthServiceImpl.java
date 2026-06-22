package com.akhil.auth_service.service.impl;

import com.akhil.auth_service.dto.*;
import com.akhil.auth_service.entity.RefreshToken;
import com.akhil.auth_service.entity.Role;
import com.akhil.auth_service.entity.Users;
import com.akhil.auth_service.exception.DuplicateResourceException;
import com.akhil.auth_service.exception.RefreshTokenExpiredException;
import com.akhil.auth_service.exception.ResourcesNotFoundException;
import com.akhil.auth_service.repository.AuthRepository;
import com.akhil.auth_service.repository.RefreshTokenRepository;
import com.akhil.auth_service.service.AuthService;
import com.akhil.auth_service.service.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthRepository repository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(RegisterReq registerReq) {
        if(repository.existsByEmail(registerReq.email())) {
            log.error("Email already exists: {}", registerReq.email());
            throw  new DuplicateResourceException("Email already exists");
        }

        Users user= Users.builder()
                .name(registerReq.name())
                .password(encoder.encode(registerReq.password()))
                .email(registerReq.email())
                .role(Role.PATIENT)
                .active(true)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

        Users saved=repository.save(user);
        return RegisterResponse.builder()
                .name(saved.getName())
                .email(saved.getEmail())
                .message("User registered successfully")
                .build();
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        Users user = repository.findByEmail(request.email()).orElseThrow(
                () -> {
                    log.error("Email not found: {}", request.email());
                    return new ResourcesNotFoundException("User not found");
                });
        return generateToken(user);
    }

    private AuthResponse generateToken(Users user) {
        refreshTokenRepository.deleteByUser(user);

        String accessToken =jwtUtil.generateAccessToken(user);
        String refreshToken= jwtUtil.generateRefreshToken(user);
        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .expiry_date(LocalDateTime.now().plusDays(7))
                .user(user)
                .build();

        refreshTokenRepository.save(token);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(900L)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken oldToken= refreshTokenRepository
                .findByToken(request.refreshToken()).orElseThrow(
                        ()->  new RefreshTokenExpiredException("Refresh Token Expired"));

        // Delete old token
        if(oldToken.getExpiry_date().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(oldToken);
            throw new BadCredentialsException("Refresh Token Expired");
        }

        refreshTokenRepository.delete(oldToken);

        // regenerate the access and  refresh token
        String newAccessToken =jwtUtil.generateAccessToken(oldToken.getUser());
        String newRefreshToken =jwtUtil.generateRefreshToken(oldToken.getUser());

        RefreshToken rotatedToken= RefreshToken.builder()
                .token(newRefreshToken)
                .expiry_date(LocalDateTime.now().plusDays(7))
                .user(oldToken.getUser())
                .build();

        refreshTokenRepository.save(rotatedToken);
        return AuthResponse
                .builder()
                .accessToken(newAccessToken)
                .tokenType("Bearer")
                .expiresIn(900L)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Override
    public String logout(String refreshToken) {
        RefreshToken token = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() ->
                        new ResourcesNotFoundException("Refresh token not found"));

        refreshTokenRepository.delete(token);
        return "Logout Successfully";
    }
}
