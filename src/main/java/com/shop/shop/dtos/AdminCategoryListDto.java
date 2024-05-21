package com.shop.shop.dtos;

import com.shop.shop.models.Category;

import java.util.List;

public record AdminCategoryListDto(
        List<CategoryDto> categories
) {
    public static AdminCategoryListDto of(List<Category> categories) {
        return new AdminCategoryListDto(
                categories.stream()
                        .map(category -> new CategoryDto(
                                category.id().toString(),
                                category.name(),
                                category.hidden()
                        ))
                        .toList()
        );
    }

    public record CategoryDto(
            String id,
            String name,
            boolean hidden
    ) {
    }
}