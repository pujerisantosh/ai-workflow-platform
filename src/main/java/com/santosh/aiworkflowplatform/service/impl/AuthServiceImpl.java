package com.santosh.aiworkflowplatform.service.impl;

import com.santosh.aiworkflowplatform.dto.request.RegisterRequest;
import com.santosh.aiworkflowplatform.dto.response.AuthResponse;
import com.santosh.aiworkflowplatform.entity.User;
import com.santosh.aiworkflowplatform.repository.UserRepository;
import com.santosh.aiworkflowplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {

        Optional<User> existingUser =
                userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        // Set other fields if they exist in your User entity
        // user.setRole(Role.USER);
        // user.setCreatedAt(LocalDateTime.now());
        // user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return new AuthResponse("User registered successfully");
    }
}

