package com.santosh.aiworkflowplatform.service;

import com.santosh.aiworkflowplatform.dto.request.LoginRequest;
import com.santosh.aiworkflowplatform.dto.request.RegisterRequest;
import com.santosh.aiworkflowplatform.dto.response.AuthResponse;
import com.santosh.aiworkflowplatform.dto.response.JwtResponse;

public   interface AuthService {


    AuthResponse register(RegisterRequest request);

    JwtResponse login(LoginRequest request);

}
