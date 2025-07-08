package com.aliyaman.noteapp.service;

import com.aliyaman.noteapp.dto.*;

public interface IAuthService {

    public UserDto register(RegisterRequest registerRequest);

    public AuthResponse login(LoginRequest request);

    public String verifyEmail(MailRequest request);
}
