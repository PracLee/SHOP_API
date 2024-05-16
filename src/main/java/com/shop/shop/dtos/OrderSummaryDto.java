package com.shop.shop.dtos;

import com.shop.shop.models.Order;

import java.time.format.DateTimeFormatter;

public record OrderSummaryDto(
        String id,
        String title,
        Long totalPrice,
        String status,
        String orderedAt
) {
    private final static DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static OrderSummaryDto of(Order order) {
        return new OrderSummaryDto(
                order.id().toString(),
                order.title(),
                order.totalPrice().asLong(),
                order.status().toString(),
                order.orderedAt().format(DATE_TIME_FORMATTER)
        );
    }
}