package com.example.studyy.repository;

import com.example.studyy.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken save(PasswordResetToken token);

    void deleteByToken(String token); // Para invalidar tokens usados



}
