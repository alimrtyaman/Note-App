package com.aliyaman.noteapp.service.impl;

import com.aliyaman.noteapp.dto.ForgotPaswordRequest;
import com.aliyaman.noteapp.dto.ResetPasswordRequest;
import com.aliyaman.noteapp.entity.PasswordReset;
import com.aliyaman.noteapp.entity.User;
import com.aliyaman.noteapp.repository.PasswordResetRepository;
import com.aliyaman.noteapp.repository.UserRepository;
import com.aliyaman.noteapp.service.IPasswordResetService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetServiceImpl implements IPasswordResetService {

    private final PasswordResetRepository passwordResetRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final EmailService emailService;

    @Autowired
    public PasswordResetServiceImpl(PasswordResetRepository passwordResetRepository, UserRepository userRepository, BCryptPasswordEncoder encoder, EmailService emailService) {
        this.passwordResetRepository = passwordResetRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.emailService = emailService;
    }


    @Override
    public String createPasswordReset(ForgotPaswordRequest request){
       User user = userRepository.findByEmailIgnoreCase(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("user not found")
        );
        PasswordReset pReset = new PasswordReset();

        pReset.setExpiredAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60));
        pReset.setUsed(false);
        pReset.setResetCode(generateCode());
        pReset.setUser(user);
        passwordResetRepository.save(pReset);
        sendVerificationEmail(user , pReset.getResetCode());
        return "Mail has been sent";
    }


    @Override
    public String resetPassword(ResetPasswordRequest request){
        PasswordReset pr = passwordResetRepository.findByResetCode(request.getVerficationCode())
                .orElseThrow(() -> new IllegalArgumentException("Code not exist"));

        User user = pr.getUser();
        user.setPassword(encoder.encode(request.getNewPassword()));
        pr.setUsed(true);
        userRepository.save(user);
        passwordResetRepository.save(pr);
        return "Password has been chanceg WELCOME TO HOME ";
    }






    public String generateCode(){
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    private void sendVerificationEmail(User user , String resetCode){
        String subject = "Password Verification";
        String verificationCode = "VERIFICATION CODE " + resetCode;
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + resetCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        try{
            emailService.sendVerificationEmail(user.getEmail(), subject , htmlMessage);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }


}
