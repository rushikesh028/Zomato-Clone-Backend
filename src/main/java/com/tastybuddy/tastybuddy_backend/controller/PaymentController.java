package com.tastybuddy.tastybuddy_backend.controller;

import com.tastybuddy.tastybuddy_backend.dto.ApiMessageResponse;
import com.tastybuddy.tastybuddy_backend.dto.PaymentCreateRequest;
import com.tastybuddy.tastybuddy_backend.dto.PaymentOrderResponse;
import com.tastybuddy.tastybuddy_backend.dto.PaymentVerificationRequest;
import com.tastybuddy.tastybuddy_backend.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.tastybuddy.tastybuddy_backend.service.OrderService;
import com.tastybuddy.tastybuddy_backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Payment order creation and gateway verification")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "Create Razorpay payment order")
    public ResponseEntity<PaymentOrderResponse> createPayment(@Valid @RequestBody PaymentCreateRequest request) throws Exception {
        Order order = orderService.getCurrentUserOrderById(request.getOrderId());
        return ResponseEntity.ok(paymentService.createOrder(order));
    }

    @PostMapping("/verify")
    @Operation(summary = "Verify Razorpay payment signature")
    public ResponseEntity<ApiMessageResponse> verifyPayment(@Valid @RequestBody PaymentVerificationRequest request) {
        boolean isValid = paymentService.verifySignature(
                request.getRazorpayOrderId(),
                request.getRazorpayPaymentId(),
                request.getRazorpaySignature()
        );

        if (!isValid) {
            throw new IllegalArgumentException("Invalid payment signature");
        }

        orderService.markOrderPaid(request.getOrderId());
        return ResponseEntity.ok(new ApiMessageResponse("Payment verified successfully"));
    }
}
