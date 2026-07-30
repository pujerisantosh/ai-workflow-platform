package com.santosh.aiworkflowplatform.service.impl;

import com.santosh.aiworkflowplatform.dto.request.RegisterRequest;
import com.santosh.aiworkflowplatform.dto.response.AuthResponse;
import com.santosh.aiworkflowplatform.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public String register() {
        return "User registered successfully";
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        return null;
    }
}