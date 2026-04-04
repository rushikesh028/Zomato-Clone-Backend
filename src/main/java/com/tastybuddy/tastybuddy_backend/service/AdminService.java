package com.tastybuddy.tastybuddy_backend.service;

import com.tastybuddy.tastybuddy_backend.dto.AdminDashboardDTO;
import com.tastybuddy.tastybuddy_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;

    public AdminDashboardDTO getDashboard() {

        long users = userRepository.count();
        long orders = orderRepository.count();
        long foods = foodRepository.count();

        Double revenue = orderRepository.getTotalRevenue();
        if (revenue == null) revenue = 0.0;

        return new AdminDashboardDTO(users, orders, revenue, foods);
    }
}
