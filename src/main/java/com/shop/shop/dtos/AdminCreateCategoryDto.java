package com.shop.shop.dtos;

import jakarta.validation.constraints.NotBlank;

public record AdminCreateCategoryDto(
        @NotBlank
        String name
) {
}