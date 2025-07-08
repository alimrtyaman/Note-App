package com.aliyaman.noteapp.controller;

import com.aliyaman.noteapp.dto.*;

public interface IAuthController {

    public UserDto register(RegisterRequest registerRequest);

    public AuthResponse login(LoginRequest request);

    public String verifyEmail(MailRequest request);

    public String createPasswordReset(ForgotPaswordRequest request);

    public String resetPassword(ResetPasswordRequest request);
}
