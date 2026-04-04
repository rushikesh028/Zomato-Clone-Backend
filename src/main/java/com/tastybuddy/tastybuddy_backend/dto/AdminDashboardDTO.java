package com.tastybuddy.tastybuddy_backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardDTO {

    private long totalUsers;
    private long totalOrders;
    private double totalRevenue;
    private long totalFoods;
}
