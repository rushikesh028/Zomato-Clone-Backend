package com.tastybuddy.tastybuddy_backend.controller;



import com.tastybuddy.tastybuddy_backend.dto.AdminDashboardDTO;
import com.tastybuddy.tastybuddy_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public AdminDashboardDTO getDashboard() {
        return adminService.getDashboard();
    }
}