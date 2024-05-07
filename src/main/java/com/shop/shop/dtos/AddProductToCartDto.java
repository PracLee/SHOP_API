package com.shop.shop.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AddProductToCartDto(
        @NotBlank
        String productId,

        List<Option> options,

        @Min(1)
        int quantity
) {
    public record Option(
            @NotBlank
            String id,

            @NotBlank
            String itemId
    ) {
    }
}