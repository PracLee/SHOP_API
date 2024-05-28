package com.shop.shop.application;

import com.shop.shop.models.Order;
import com.shop.shop.models.OrderId;
import com.shop.shop.models.OrderStatus;
import com.shop.shop.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateOrderService {
    private final OrderRepository orderRepository;

    public UpdateOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void updateOrderStatus(OrderId orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        order.changeStatus(status);
    }
}