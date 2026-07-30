package com.santosh.aiworkflowplatform.service;

import com.santosh.aiworkflowplatform.dto.request.RegisterRequest;
import com.santosh.aiworkflowplatform.dto.response.AuthResponse;

public interface AuthService {

    String register();

    AuthResponse register(RegisterRequest request);
}