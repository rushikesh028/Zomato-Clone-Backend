package com.tastybuddy.tastybuddy_backend.service;



import com.tastybuddy.tastybuddy_backend.dto.OrderItemResponse;
import com.tastybuddy.tastybuddy_backend.dto.OrderResponse;
import com.tastybuddy.tastybuddy_backend.entity.*;
import com.tastybuddy.tastybuddy_backend.exception.BadRequestException;
import com.tastybuddy.tastybuddy_backend.exception.ResourceNotFoundException;
import com.tastybuddy.tastybuddy_backend.exception.UnauthorizedException;
import com.tastybuddy.tastybuddy_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final FoodRepository foodRepository;
    private final CurrentUserService currentUserService;

    @Transactional
    public OrderResponse placeOrder() {

        String email = currentUserService.getCurrentUserEmail();

        List<CartItem> cartItems = cartRepository.findByUserEmail(email);

        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        double total = 0;

        for (CartItem item : cartItems) {
            FoodItem food = foodRepository.findById(item.getFoodId())
                    .orElseThrow(() -> new ResourceNotFoundException("Food not found for cart item " + item.getFoodId()));
            item.setFoodName(food.getName());
            total += item.getQuantity() * food.getPrice();
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

        return toResponse(savedOrder);
    }

    public List<OrderResponse> getUserOrders() {
        String email = currentUserService.getCurrentUserEmail();
        return orderRepository.findByUserEmail(email)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponse getCurrentUserOrderResponse(Long id) {
        return toResponse(getCurrentUserOrderById(id));
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public Order getCurrentUserOrderById(Long id) {
        String email = currentUserService.getCurrentUserEmail();
        Order order = getOrderById(id);
        if (!order.getUserEmail().equalsIgnoreCase(email)) {
            throw new UnauthorizedException("You do not have access to this order");
        }
        return order;
    }

    @Transactional
    public OrderResponse markOrderPaid(Long orderId) {
        Order order = getCurrentUserOrderById(orderId);
        order.setStatus("PAID");
        return toResponse(orderRepository.save(order));
    }

    public OrderResponse toAdminOrderResponse(Order order) {
        return toResponse(order);
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = orderItemRepository.findByOrderId(order.getId())
                .stream()
                .map(item -> new OrderItemResponse(item.getFoodId(), item.getFoodName(), item.getQuantity()))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getUserEmail(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                items
        );
    }
}
