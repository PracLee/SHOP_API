package com.shop.shop.dtos;

import com.shop.shop.models.Category;
import com.shop.shop.models.Product;

import java.util.List;
import java.util.stream.IntStream;

public record ProductDetailDto(
        String id,
        CategoryDto category,
        List<ImageDto> images,
        String name,
        Long price,
        List<ProductOptionDto> options,
        String description
) {
    public static ProductDetailDto of(Product product, Category category) {
        return new ProductDetailDto(
                product.id().toString(),
                CategoryDto.of(category),
                IntStream.range(0, product.imagesSize())
                        .mapToObj(i -> ImageDto.of(product.image(i)))
                        .toList(),
                product.name(),
                product.price().asLong(),
                IntStream.range(0, product.optionSize())
                        .mapToObj(i -> ProductOptionDto.of(product.option(i)))
                        .toList(),
                product.description()
        );
    }
}