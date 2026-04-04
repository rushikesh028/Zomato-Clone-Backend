package com.tastybuddy.tastybuddy_backend.service;



import com.tastybuddy.tastybuddy_backend.entity.*;
import com.tastybuddy.tastybuddy_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;

    public String placeOrder() {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        List<CartItem> cartItems = cartRepository.findByUserEmail(email);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double total = 0;

        for (CartItem item : cartItems) {
            total += item.getQuantity() * 100; // dummy price
        }

        Order order = Order.builder()
                .userEmail(email)
                .totalAmount(total)
                .status("PLACED")
                .createdAt(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);

        for (CartItem cart : cartItems) {
            OrderItem item = OrderItem.builder()
                    .orderId(savedOrder.getId())
                    .foodId(cart.getFoodId())
                    .foodName(cart.getFoodName())
                    .quantity(cart.getQuantity())
                    .build();

            orderItemRepository.save(item);
        }

        cartRepository.deleteAll(cartItems);

        return "Order placed successfully ";
    }

    public List<Order> getUserOrders() {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return orderRepository.findByUserEmail(email);
    }


    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}