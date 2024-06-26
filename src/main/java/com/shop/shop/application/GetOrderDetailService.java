package com.shop.shop.application;

import com.shop.shop.dtos.ImageDto;
import com.shop.shop.dtos.OrderDetailDto;
import com.shop.shop.dtos.OrderLineItemDto;
import com.shop.shop.models.*;
import com.shop.shop.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
public class GetOrderDetailService {
    private final static DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final OrderRepository orderRepository;

    public GetOrderDetailService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDetailDto getOrderDetail(OrderId orderId, UserId userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow();

        return new OrderDetailDto(
                order.id().toString(),
                order.title(),
                lineItems(order),
                order.totalPrice().asLong(),
                order.status().toString(),
                order.orderedAt().format(DATE_TIME_FORMATTER)
        );
    }

    private List<OrderLineItemDto> lineItems(Order order) {
        return IntStream.range(0, order.lineItemSize())
                .mapToObj(order::lineItem)
                .map(this::orderLineItem)
                .toList();
    }

    private OrderLineItemDto orderLineItem(OrderLineItem lineItem) {
        return new OrderLineItemDto(
                lineItem.id().toString(),
                product(lineItem),
                options(lineItem),
                lineItem.unitPrice().asLong(),
                lineItem.quantity(),
                lineItem.totalPrice().asLong()
        );
    }

    private OrderLineItemDto.ProductDto product(OrderLineItem lineItem) {
        return new OrderLineItemDto.ProductDto(
                lineItem.productId().toString(),
                new ImageDto(""),
                lineItem.productName()
        );
    }

    private List<OrderLineItemDto.OptionDto> options(OrderLineItem lineItem) {
        return IntStream.range(0, lineItem.optionSize())
                .mapToObj(lineItem::option)
                .map(this::orderOption)
                .toList();
    }

    private OrderLineItemDto.OptionDto orderOption(OrderOption orderOption) {
        String optionName = orderOption.name();
        String optionItemName = orderOption.optionItem().name();

        return new OrderLineItemDto.OptionDto(
                optionName,
                new OrderLineItemDto.OptionItemDto(optionItemName));
    }
}