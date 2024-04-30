package com.shop.shop.dtos;

import com.shop.shop.models.Category;

public record CategoryDto(
        String id,
        String name
) {
    public static CategoryDto of(Category category) {
        return new CategoryDto(
                category.id().toString(),
                category.name()
        );
    }
}
