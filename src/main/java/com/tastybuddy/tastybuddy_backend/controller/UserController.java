package com.tastybuddy.tastybuddy_backend.controller;

import com.tastybuddy.tastybuddy_backend.dto.RegisterRequest;
import com.tastybuddy.tastybuddy_backend.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.tastybuddy.tastybuddy_backend.service.CurrentUserService;
import com.tastybuddy.tastybuddy_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User registration and profile operations")
public class UserController {

    private final UserService userService;
    private final CurrentUserService currentUserService;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user profile", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.findByEmail(currentUserService.getCurrentUserEmail()));
    }
}
