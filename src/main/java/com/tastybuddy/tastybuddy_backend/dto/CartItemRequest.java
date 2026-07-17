package com.tastybuddy.tastybuddy_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequest {

    @NotNull(message = "Food id is required")
    private Long foodId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
