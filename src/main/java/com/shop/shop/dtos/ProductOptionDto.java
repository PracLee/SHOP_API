package com.shop.shop.dtos;


import com.shop.shop.models.ProductOption;

import java.util.List;
import java.util.stream.IntStream;

public record ProductOptionDto(
        String id,
        String name,
        List<ProductOptionItemDto> items
) {
    public static ProductOptionDto of(ProductOption option) {
        return new ProductOptionDto(
                option.id().toString(),
                option.name(),
                IntStream.range(0, option.itemSize())
                        .mapToObj(i -> ProductOptionItemDto.of(option.item(i)))
                        .toList()
        );
    }
}