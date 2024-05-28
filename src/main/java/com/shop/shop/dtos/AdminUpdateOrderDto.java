package com.shop.shop.dtos;

import jakarta.validation.constraints.NotBlank;

public record AdminUpdateOrderDto(
        @NotBlank
        String status
) {
}