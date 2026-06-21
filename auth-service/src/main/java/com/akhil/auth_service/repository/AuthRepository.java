package com.akhil.auth_service.repository;

import com.akhil.auth_service.entity.Users;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Users,Long> {

    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);
}
