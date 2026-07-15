package com.tastybuddy.tastybuddy_backend.service;

import com.tastybuddy.tastybuddy_backend.dto.AdminDashboardDTO;
import com.tastybuddy.tastybuddy_backend.dto.OrderResponse;
import com.tastybuddy.tastybuddy_backend.dto.UserResponse;
import com.tastybuddy.tastybuddy_backend.entity.Order;
import com.tastybuddy.tastybuddy_backend.exception.BadRequestException;
import com.tastybuddy.tastybuddy_backend.exception.ResourceNotFoundException;
import com.tastybuddy.tastybuddy_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final UserService userService;
    private final OrderService orderService;

    private static final Set<String> ALLOWED_ORDER_STATUSES = Set.of("PLACED", "PAID", "PREPARING", "OUT_FOR_DELIVERY", "DELIVERED", "CANCELLED");

    public AdminDashboardDTO getDashboard() {

        long users = userRepository.count();
        long orders = orderRepository.count();
        long foods = foodRepository.count();

        Double revenue = orderRepository.getTotalRevenue();
        if (revenue == null) revenue = 0.0;

        return new AdminDashboardDTO(users, orders, revenue, foods);
    }

    public List<UserResponse> getUsers() {
        return userService.getAllUsers();
    }

    public List<OrderResponse> getOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(orderService::toAdminOrderResponse)
                .toList();
    }

    public OrderResponse updateOrderStatus(Long orderId, String status) {
        String normalizedStatus = status.trim().toUpperCase();
        if (!ALLOWED_ORDER_STATUSES.contains(normalizedStatus)) {
            throw new BadRequestException("Unsupported order status: " + status);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(normalizedStatus);
        return orderService.toAdminOrderResponse(orderRepository.save(order));
    }
}
