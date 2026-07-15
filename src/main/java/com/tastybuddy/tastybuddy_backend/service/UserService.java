package com.tastybuddy.tastybuddy_backend.service;


import com.tastybuddy.tastybuddy_backend.dto.RegisterRequest;
import com.tastybuddy.tastybuddy_backend.dto.UserResponse;
import com.tastybuddy.tastybuddy_backend.entity.User;
import com.tastybuddy.tastybuddy_backend.exception.DuplicateResourceException;
import com.tastybuddy.tastybuddy_backend.exception.ResourceNotFoundException;
import com.tastybuddy.tastybuddy_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail().trim().toLowerCase()).isPresent()) {
            throw new DuplicateResourceException("A user with this email already exists");
        }

        User user = User.builder()
                .name(request.getName().trim())
                .email(request.getEmail().trim().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        return toResponse(userRepository.save(user));
    }

    public UserResponse findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAllByOrderByIdDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
