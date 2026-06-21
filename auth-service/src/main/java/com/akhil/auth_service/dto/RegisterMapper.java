package com.akhil.auth_service.dto;

import com.akhil.auth_service.entity.Users;

import java.time.LocalDateTime;

public class RegisterMapper {

    public Users toEntity(RegisterReq registerReq) {

        return Users.builder()
                .name(registerReq.name())
                .password(registerReq.password())
                .email(registerReq.email())
                .active(true)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

    }
}

