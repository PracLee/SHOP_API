package com.shop.shop.application;

import com.shop.shop.Fixtures;
import com.shop.shop.models.*;
import com.shop.shop.repositories.CartRepository;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static com.shop.shop.TestUtils.createCartLineItemOption;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateOrderServiceTest {
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private OrderRepository orderRepository;

    private CreateOrderService createOrderService;

    private Product product;
    private Set<CartLineItemOption> options;
    private int quantity;

    private Receiver receiver;
    private Payment payment;

    @BeforeEach
    void setUpMockObjects() {
        productRepository = mock(ProductRepository.class);
        cartRepository = mock(CartRepository.class);
        orderRepository = mock(OrderRepository.class);

        createOrderService = new CreateOrderService(
                productRepository, cartRepository, orderRepository);
    }

    @BeforeEach
    void setUpFixtures() {
        product = Fixtures.product("맨투맨");

        options = Set.of(
                createCartLineItemOption(product, 0, 0),
                createCartLineItemOption(product, 1, 0)
        );

        quantity = 1;

        receiver = new Receiver(
                "홍길동",
                new Address(
                        "서울특별시 성동구 상원12길 34",
                        "ㅇㅇㅇ호",
                        new PostalCode("04790")
                ),
                new PhoneNumber("01012345678")
        );

        payment = new Payment(
                "ORDER-20230101-00000001",
                "123456789012"
        );
    }

    @Test
    void createOrder() {
        UserId userId = UserId.generate();

        Cart cart = new Cart(userId);
        cart.addProduct(product, options, quantity);

        given(cartRepository.findByUserId(userId))
                .willReturn(Optional.of(cart));

        given(productRepository.findById(product.id()))
                .willReturn(Optional.of(product));

        Order order = createOrderService.createOrder(userId, receiver, payment);

        assertThat(order.lineItemSize()).isEqualTo(1);

        verify(orderRepository).save(order);
    }
}