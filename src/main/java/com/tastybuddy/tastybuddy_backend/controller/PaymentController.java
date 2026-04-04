package com.tastybuddy.tastybuddy_backend.controller;

import com.tastybuddy.tastybuddy_backend.entity.Order;
//import com.tastybuddy.tastybuddy_backend.model.Order;
import com.tastybuddy.tastybuddy_backend.repository.OrderRepository;
import com.tastybuddy.tastybuddy_backend.service.PaymentService;
import com.tastybuddy.tastybuddy_backend.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

        // Create Payment
        @PostMapping("/create")
        public String createPayment(@RequestParam double amount) throws Exception {
            return paymentService.createOrder(amount);
        }

        //  VERIFY PAYMENT (ADD HERE)
        @PostMapping("/verify")
        public ResponseEntity<String> verifyPayment(
                @RequestParam String razorpayOrderId,
                @RequestParam String razorpayPaymentId,
                @RequestParam String razorpaySignature,
                @RequestParam Long orderId
        ) {

            boolean isValid = paymentService.verifySignature(
                    razorpayOrderId,
                    razorpayPaymentId,
                    razorpaySignature
            );

            if (!isValid) {
                return ResponseEntity.badRequest().body("Invalid Payment ");
            }

            Order order = orderService.getOrderById(orderId);
            order.setStatus("PAID");

            orderRepository.save(order);

            return ResponseEntity.ok("Payment Verified & Successful ");
        }
    }

























//@RestController
//@RequestMapping("/api/payment")
//public class PaymentController {
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    // 🔹 STEP 4 - Create Payment Order
//    @PostMapping("/create")
//    public String createPayment(@RequestParam double amount) throws Exception {
//        return paymentService.createOrder(amount);
//    }

// 🔹 STEP 5 - VERIFY PAYMENT (ADD THIS)
//    @PostMapping("/verify")
//    public ResponseEntity<String> verifyPayment(@RequestParam Long orderId) {
//
//        // 🔥 Fetch order from DB
//        Order order = orderService.getOrderById(orderId);
//
//        // ✅ Update status after payment success
//        order.setStatus("PAID");
//
//        orderRepository.save(order);
//
//        return ResponseEntity.ok("Payment Successful ✅");
//    }
