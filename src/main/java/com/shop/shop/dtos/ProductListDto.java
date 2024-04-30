package com.shop.shop.dtos;

import java.util.List;

public record ProductListDto(
        List<ProductSummaryDto> products
) {
}
