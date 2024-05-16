package com.shop.shop.models;

import com.shop.shop.Fixtures;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.shop.shop.TestUtils.createOrderOptions;
import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {
    @Test
    void creation() {
        Product product1 = Fixtures.product("맨투맨");
        Product product2 = Fixtures.product("셔츠");

        List<OrderLineItem> lineItems = List.of(
                new OrderLineItem(
                        OrderLineItemId.generate(),
                        product1,
                        createOrderOptions(product1, new int[]{0, 0}),
                        1
                ),
                new OrderLineItem(
                        OrderLineItemId.generate(),
                        product1,
                        createOrderOptions(product1, new int[]{1, 1}),
                        2
                ),
                new OrderLineItem(
                        OrderLineItemId.generate(),
                        product2,
                        List.of(),
                        2
                )
        );

        Receiver receiver = Fixtures.receiver("홍길동");
        Payment payment = Fixtures.payment();

        Order order = new Order(OrderId.generate(), UserId.generate(),
                lineItems, receiver, payment, OrderStatus.PAID);

        assertThat(order.lineItemSize()).isEqualTo(3);

        assertThat(order.totalPrice())
                .isEqualTo(new Money(138_000L + 128_000L * 2 + 123_000L * 2));
    }
}