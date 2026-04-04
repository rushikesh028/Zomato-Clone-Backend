package com.tastybuddy.tastybuddy_backend.controller;
import com.tastybuddy.tastybuddy_backend.dto.LoginRequest;
import com.tastybuddy.tastybuddy_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
