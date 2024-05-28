package com.shop.shop.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AdminCreateProductDto(
        @NotBlank
        String categoryId,

        List<ImageDto> images,

        @NotBlank
        String name,

        @Min(0)
        Long price,

        List<OptionDto> options,

        @NotBlank
        String description
) {
    public record ImageDto(
            @NotBlank
            String url
    ) {
    }

    public record OptionDto(
            @NotBlank
            String name,

            @NotEmpty
            List<OptionItemDto> items
    ) {
    }

    public record OptionItemDto(
            @NotBlank
            String name
    ) {
    }
}