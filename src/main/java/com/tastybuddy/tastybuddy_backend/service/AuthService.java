package com.tastybuddy.tastybuddy_backend.service;


import com.tastybuddy.tastybuddy_backend.dto.AuthResponse;
import com.tastybuddy.tastybuddy_backend.dto.LoginRequest;
import com.tastybuddy.tastybuddy_backend.dto.UserResponse;
import com.tastybuddy.tastybuddy_backend.entity.User;
import com.tastybuddy.tastybuddy_backend.exception.UnauthorizedException;
import com.tastybuddy.tastybuddy_backend.repository.UserRepository;
import com.tastybuddy.tastybuddy_backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    public AuthResponse login(LoginRequest request) {

        String normalizedEmail = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        return new AuthResponse(
                jwtUtil.generateToken(user.getEmail(), user.getRole()),
                "Bearer",
                expirationMs,
                new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole())
        );
    }
}
