package com.tastybuddy.tastybuddy_backend.dto;

public record CartItemResponse(
        Long id,
        Long foodId,
        String foodName,
        int quantity
) {
}
