package com.tastybuddy.tastybuddy_backend.dto;

public record FoodResponse(
        Long id,
        String name,
        double price,
        String category,
        String imageUrl
) {
}
