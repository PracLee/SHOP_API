package com.shop.shop.application;

import com.shop.shop.models.*;
import com.shop.shop.repositories.CartRepository;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional
public class CreateOrderService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public CreateOrderService(ProductRepository productRepository,
                              CartRepository cartRepository,
                              OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(
            UserId userId, Receiver receiver, Payment payment) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow();

        List<OrderLineItem> lineItems = IntStream
                .range(0, cart.lineItemSize())
                .mapToObj(cart::lineItem)
                .map(this::createOrderLineItem)
                .toList();

        OrderId orderId = OrderId.generate();

        Order order = new Order(orderId, userId, lineItems, receiver, payment, OrderStatus.PAID);

        orderRepository.save(order);

        cart.clear();

        return order;
    }

    private OrderLineItem createOrderLineItem(CartLineItem cartLineItem) {
        Product product = productRepository.findById(cartLineItem.productId())
                .orElseThrow();

        List<OrderOption> options = cartLineItem.optionIds().stream()
                .map(optionId ->
                        createOrderOption(cartLineItem, product, optionId))
                .toList();

        return new OrderLineItem(
                OrderLineItemId.generate(),
                product,
                options,
                cartLineItem.quantity()
        );
    }

    private static OrderOption createOrderOption(
            CartLineItem cartLineItem, Product product,
            ProductOptionId optionId) {
        ProductOptionItemId itemId = cartLineItem.optionItemId(optionId);

        ProductOption productOption = product.optionById(optionId);
        ProductOptionItem productOptionItem = productOption.itemById(itemId);

        return new OrderOption(
                OrderOptionId.generate(),
                productOption,
                productOptionItem
        );
    }
}