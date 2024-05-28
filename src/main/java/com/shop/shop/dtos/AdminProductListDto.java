package com.shop.shop.dtos;

import java.util.List;

public record AdminProductListDto(
        List<AdminProductSummaryDto> products
) {
}