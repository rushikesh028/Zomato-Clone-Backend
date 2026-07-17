package com.tastybuddy.tastybuddy_backend.dto;

public record OrderItemResponse(
        Long foodId,
        String foodName,
        int quantity
) {
}
