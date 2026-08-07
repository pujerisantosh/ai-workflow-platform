package com.santosh.aiworkflowplatform.service;

import com.santosh.aiworkflowplatform.dto.request.RegisterRequest;
import com.santosh.aiworkflowplatform.dto.response.AuthResponse;

public   interface AuthService {


    AuthResponse register(RegisterRequest request);
}
