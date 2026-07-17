package com.tastybuddy.tastybuddy_backend.dto;

public record PaymentOrderResponse(
        String key,
        String gatewayOrderId,
        long amount,
        String currency,
        Long orderId,
        String status
) {
}
