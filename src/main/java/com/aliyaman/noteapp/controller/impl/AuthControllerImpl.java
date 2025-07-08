package com.aliyaman.noteapp.controller.impl;

import com.aliyaman.noteapp.controller.IAuthController;
import com.aliyaman.noteapp.dto.*;
import com.aliyaman.noteapp.service.IAuthService;
import com.aliyaman.noteapp.service.IPasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthControllerImpl implements IAuthController {

    private final IAuthService iAuthService;
    private final IPasswordResetService passwordResetService;

    @PostMapping(path = "/register")
    @Override
    public UserDto register(@RequestBody RegisterRequest registerRequest) {
        return iAuthService.register(registerRequest);
    }

    @PostMapping(path = "/login")
    @Override
    public AuthResponse login(@RequestBody LoginRequest request) {
        return iAuthService.login(request);
    }

    @PostMapping("/verify")
    @Override
    public String verifyEmail(@RequestBody MailRequest request) {
        return iAuthService.verifyEmail(request);
    }

    @PostMapping("/forgot-password")
    @Override
    public String createPasswordReset(@RequestBody ForgotPaswordRequest request) {
        return passwordResetService.createPasswordReset(request);
    }

    @PostMapping("/reset-password")
    @Override
    public String resetPassword(@RequestBody ResetPasswordRequest request) {
        return passwordResetService.resetPassword(request);
    }
}
