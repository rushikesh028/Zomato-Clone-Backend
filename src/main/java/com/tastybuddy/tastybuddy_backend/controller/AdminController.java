package com.tastybuddy.tastybuddy_backend.controller;

import com.tastybuddy.tastybuddy_backend.dto.AdminDashboardDTO;
import com.tastybuddy.tastybuddy_backend.dto.OrderResponse;
import com.tastybuddy.tastybuddy_backend.dto.OrderStatusUpdateRequest;
import com.tastybuddy.tastybuddy_backend.dto.UserResponse;
import com.tastybuddy.tastybuddy_backend.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Administrative reporting endpoints")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    @Operation(summary = "Get admin dashboard metrics")
    public ResponseEntity<AdminDashboardDTO> getDashboard() {
        return ResponseEntity.ok(adminService.getDashboard());
    }

    @GetMapping("/users")
    @Operation(summary = "List users")
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(adminService.getUsers());
    }

    @GetMapping("/orders")
    @Operation(summary = "List all orders")
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return ResponseEntity.ok(adminService.getOrders());
    }

    @PatchMapping("/orders/{id}/status")
    @Operation(summary = "Update order status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long id,
                                                           @Valid @RequestBody OrderStatusUpdateRequest request) {
        return ResponseEntity.ok(adminService.updateOrderStatus(id, request.getStatus()));
    }
}
