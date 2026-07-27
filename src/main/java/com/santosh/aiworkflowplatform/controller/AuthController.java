package com.santosh.aiworkflowplatform.controller;

import com.santosh.aiworkflowplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register() {
        return authService.register();
    }
}