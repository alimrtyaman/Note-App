package com.aliyaman.noteapp.service;

import com.aliyaman.noteapp.dto.ForgotPaswordRequest;
import com.aliyaman.noteapp.dto.ResetPasswordRequest;

public interface IPasswordResetService {

    public String createPasswordReset(ForgotPaswordRequest request);

    public String resetPassword(ResetPasswordRequest request);
}
