package com.aliyaman.noteapp.service.impl;

import com.aliyaman.noteapp.repository.UserRepository;
import com.aliyaman.noteapp.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;


}
