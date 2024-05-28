package com.shop.shop.application;

import com.shop.shop.dtos.AdminOrderListDto;
import com.shop.shop.dtos.AdminOrderSummaryDto;
import com.shop.shop.dtos.OrderListDto;
import com.shop.shop.dtos.OrderSummaryDto;
import com.shop.shop.models.Order;
import com.shop.shop.models.User;
import com.shop.shop.models.UserId;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetOrderListService {
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    public GetOrderListService(OrderRepository orderRepository,
                               UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public OrderListDto getOrderList(UserId userId) {
        List<Order> orders =
                orderRepository.findAllByUserIdOrderByIdDesc(userId);

        return new OrderListDto(
                orders.stream()
                        .map(OrderSummaryDto::of)
                        .toList()
        );
    }

    public AdminOrderListDto getAdminOrderList() {
        List<Order> orders = orderRepository.findAllByOrderByIdDesc();
        List<UserId> userIds = orders.stream()
                .map(Order::userId)
                .toList();
        List<User> users = userRepository.findAllByIdIn(userIds);

        return new AdminOrderListDto(
                orders.stream()
                        .map(order -> {
                            User user = users.stream()
                                    .filter(u -> u.id().equals(order.userId()))
                                    .findFirst()
                                    .orElseThrow();
                            return AdminOrderSummaryDto.of(order, user);
                        })
                        .toList()
        );
    }
}