package com.aliyaman.noteapp.repository;

import com.aliyaman.noteapp.dto.ResetPasswordRequest;
import com.aliyaman.noteapp.entity.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {


    Optional<PasswordReset> findByResetCode(String resetCode);
}
