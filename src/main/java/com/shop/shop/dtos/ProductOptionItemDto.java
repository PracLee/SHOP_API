package com.shop.shop.dtos;

import com.shop.shop.models.ProductOptionItem;

public record ProductOptionItemDto(
        String id,
        String name
) {
    public static ProductOptionItemDto of(ProductOptionItem item) {
        return new ProductOptionItemDto(
                item.id().toString(),
                item.name()
        );
    }
}
