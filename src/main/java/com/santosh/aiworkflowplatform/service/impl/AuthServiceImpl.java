package com.santosh.aiworkflowplatform.service.impl;

import com.santosh.aiworkflowplatform.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public String register() {
        return "User registered successfully";
    }
}