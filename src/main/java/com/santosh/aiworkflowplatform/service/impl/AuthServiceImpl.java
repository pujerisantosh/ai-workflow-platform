package com.santosh.aiworkflowplatform.service.impl;

import com.santosh.aiworkflowplatform.dto.request.LoginRequest;
import com.santosh.aiworkflowplatform.dto.request.RegisterRequest;
import com.santosh.aiworkflowplatform.dto.response.AuthResponse;
import com.santosh.aiworkflowplatform.dto.response.JwtResponse;
import com.santosh.aiworkflowplatform.entity.Role;
import com.santosh.aiworkflowplatform.entity.User;
import com.santosh.aiworkflowplatform.exception.UserAlreadyExistsException;
import com.santosh.aiworkflowplatform.repository.UserRepository;
import com.santosh.aiworkflowplatform.security.JwtService;
import com.santosh.aiworkflowplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        Optional<User> existingUser =
                userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        userRepository.save(user);

        return new AuthResponse("User registered successfully");
    }

    @Override
    public JwtResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String token = jwtService.generateToken(
                authentication.getName(),
                user.getRole().name()
        );

        return new JwtResponse(token, "Bearer");
    }
}