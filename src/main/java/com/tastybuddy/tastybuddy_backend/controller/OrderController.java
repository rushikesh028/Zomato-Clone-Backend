package com.tastybuddy.tastybuddy_backend.controller;

import com.tastybuddy.tastybuddy_backend.dto.OrderResponse;
import com.tastybuddy.tastybuddy_backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order placement and order history")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Place order from current cart")
    public ResponseEntity<OrderResponse> placeOrder() {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder());
    }

    @GetMapping
    @Operation(summary = "Get current user's orders")
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return ResponseEntity.ok(orderService.getUserOrders());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order details for current user")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getCurrentUserOrderResponse(id));
    }
}
