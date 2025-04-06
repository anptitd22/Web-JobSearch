package com.project.webIT.repositories;

import com.project.webIT.models.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByTokenAndIsActiveTrue(String token);

    Optional<PasswordResetToken> findByUserId(Long userId);
}
