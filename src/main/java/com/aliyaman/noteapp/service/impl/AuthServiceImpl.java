package com.aliyaman.noteapp.service.impl;

import com.aliyaman.noteapp.dto.*;
import com.aliyaman.noteapp.entity.CustomUserDetails;
import com.aliyaman.noteapp.entity.User;
import com.aliyaman.noteapp.mapper.UserMapper;
import com.aliyaman.noteapp.repository.UserRepository;
import com.aliyaman.noteapp.security.JwtService;
import com.aliyaman.noteapp.service.IAuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final AuthenticationProvider authenticationProvider;

    private final UserMapper userMapper;

    private final EmailService mailService;

    @Override
    public UserDto register(RegisterRequest registerRequest){
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw  new IllegalArgumentException("Email is exist");
        }
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        user.setVerificationCode(generateVerficationCode());
        user.setEnabled(false);
        user.setVerificationExpiresAt(LocalDateTime.now().plusHours(2));
        userRepository.save(user);
        sendVerificationEmail(user);
        return userMapper.toDto(user);
    }

    @Override
    public AuthResponse login(LoginRequest request){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("USER NOT EXIST"));

            CustomUserDetails userDetails = new CustomUserDetails(user);
            String token = jwtService.generateToken(userDetails);

            return new AuthResponse(token);
        }catch (Exception e){
            log.warn("Invalid UserEmail or Password " + e.getMessage());
            return null;
        }
    }

    @Override
    public String verifyEmail(MailRequest request){
        Optional<User> optional = userRepository.findByEmailIgnoreCase(request.getMail());
        if (optional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User user = optional.get();
        if (!request.getCode().equalsIgnoreCase(user.getVerificationCode())){
            throw new RuntimeException("Invalid verfifcation code");
        }
        user.setEnabled(true);
        user.setVerificationCode(null);
        user.setVerificationExpiresAt(null);
        userRepository.save(user);
        return "Hesabiniz dogrulanmistir";

    }



    public String generateVerficationCode(){
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
    private void sendVerificationEmail(User user){
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        try{
            mailService.sendVerificationEmail(user.getEmail(), subject , htmlMessage);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }



}
