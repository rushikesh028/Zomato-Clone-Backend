package com.tastybuddy.tastybuddy_backend.controller;


import com.tastybuddy.tastybuddy_backend.entity.User;
import com.tastybuddy.tastybuddy_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping("/{email}")
    public User getUser (@PathVariable String email) {
        return userService.findByEmail(email);
    }


    @GetMapping("/me")
    public String getCurrentUser() {
        return "Logged in user: " +
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
