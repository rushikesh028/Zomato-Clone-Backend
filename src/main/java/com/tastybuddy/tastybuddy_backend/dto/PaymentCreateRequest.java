package com.tastybuddy.tastybuddy_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentCreateRequest {

    @NotNull(message = "Order id is required")
    private Long orderId;
}
