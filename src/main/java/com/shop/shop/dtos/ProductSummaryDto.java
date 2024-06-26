package com.shop.shop.dtos;

import com.shop.shop.models.Category;
import com.shop.shop.models.Product;

public record ProductSummaryDto(
        String id,
        CategoryDto category,
        ImageDto thumbnail,
        String name,
        Long price
) {
    public static ProductSummaryDto of(Product product, Category category) {
        return new ProductSummaryDto(
                product.id().toString(),
                CategoryDto.of(category),
                ImageDto.of(product.image(0)),
                product.name(),
                product.price().asLong()
        );
    }
}
