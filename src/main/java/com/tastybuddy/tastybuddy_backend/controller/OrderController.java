package com.tastybuddy.tastybuddy_backend.controller;



import com.tastybuddy.tastybuddy_backend.entity.Order;
import com.tastybuddy.tastybuddy_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Place Order
    @PostMapping
    public String placeOrder() {
        return orderService.placeOrder();
    }

    // Order History
    @GetMapping
    public List<Order> getOrders() {
        return orderService.getUserOrders();
    }
}
