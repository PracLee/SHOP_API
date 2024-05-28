package com.shop.shop.dtos;

import java.util.List;

public record AdminOrderListDto(
        List<AdminOrderSummaryDto> orders
) {
}