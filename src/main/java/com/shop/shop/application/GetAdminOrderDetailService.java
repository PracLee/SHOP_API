package com.shop.shop.application;

import com.shop.shop.dtos.AdminOrderDetailDto;
import com.shop.shop.dtos.ImageDto;
import com.shop.shop.dtos.OrderLineItemDto;
import com.shop.shop.models.*;
import com.shop.shop.repositories.OrderRepository;
import com.shop.shop.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
public class GetAdminOrderDetailService {
    private final static DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    public GetAdminOrderDetailService(OrderRepository orderRepository,
                                      UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public AdminOrderDetailDto getOrderDetail(OrderId orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow();
        User user = userRepository.findById(order.userId())
                .orElseThrow();

        return new AdminOrderDetailDto(
                order.id().toString(),
                new AdminOrderDetailDto.UserDto(user.name()),
                order.title(),
                mapToLineItemDtos(order),
                order.totalPrice().asLong(),
                AdminOrderDetailDto.ReceiverDto.of(order.receiver()),
                AdminOrderDetailDto.PaymentDto.of(order.payment()),
                order.status().toString(),
                order.orderedAt().format(DATE_TIME_FORMATTER)
        );
    }

    private List<OrderLineItemDto> mapToLineItemDtos(Order order) {
        return IntStream.range(0, order.lineItemSize())
                .mapToObj(order::lineItem)
                .map(this::mapToLineItemDto)
                .toList();
    }

    private OrderLineItemDto mapToLineItemDto(OrderLineItem lineItem) {
        return new OrderLineItemDto(
                lineItem.id().toString(),
                mapToProductDto(lineItem),
                mapToOptionDtos(lineItem),
                lineItem.unitPrice().asLong(),
                lineItem.quantity(),
                lineItem.totalPrice().asLong()
        );
    }

    private OrderLineItemDto.ProductDto mapToProductDto(
            OrderLineItem lineItem) {
        return new OrderLineItemDto.ProductDto(
                lineItem.productId().toString(),
                new ImageDto(""),
                lineItem.productName()
        );
    }

    private List<OrderLineItemDto.OptionDto> mapToOptionDtos(
            OrderLineItem lineItem) {
        return IntStream.range(0, lineItem.optionSize())
                .mapToObj(lineItem::option)
                .map(this::mapToOptionDto)
                .toList();
    }

    private OrderLineItemDto.OptionDto mapToOptionDto(OrderOption orderOption) {
        String optionName = orderOption.name();
        String optionItemName = orderOption.optionItem().name();

        return new OrderLineItemDto.OptionDto(
                optionName,
                new OrderLineItemDto.OptionItemDto(optionItemName));
    }
}