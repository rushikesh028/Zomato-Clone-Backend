package com.tastybuddy.tastybuddy_backend.service;


import com.tastybuddy.tastybuddy_backend.dto.LoginRequest;
import com.tastybuddy.tastybuddy_backend.entity.User;
import com.tastybuddy.tastybuddy_backend.repository.UserRepository;
import com.tastybuddy.tastybuddy_backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;



    public String login (LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }
}
