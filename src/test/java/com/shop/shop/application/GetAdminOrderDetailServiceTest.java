package com.shop.shop.application;

import com.shop.shop.Fixtures;
import com.shop.shop.dtos.AdminOrderDetailDto;
import com.shop.shop.models.Order;
import com.shop.shop.models.User;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetAdminOrderDetailServiceTest {
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    private GetAdminOrderDetailService getAdminOrderDetailService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        userRepository = mock(UserRepository.class);

        getAdminOrderDetailService = new GetAdminOrderDetailService(orderRepository, userRepository);
    }

    @Test
    void getOrderDetail() {
        User user = Fixtures.user("tester");
        Order order = Fixtures.order(user);

        given(orderRepository.findById(order.id()))
                .willReturn(Optional.of(order));
        given(userRepository.findById(user.id()))
                .willReturn(Optional.of(user));

        AdminOrderDetailDto orderDto =
                getAdminOrderDetailService.getOrderDetail(order.id());

        assertThat(orderDto.lineItems()).hasSize(1);
    }
}