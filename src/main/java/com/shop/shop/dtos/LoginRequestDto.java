package com.shop.shop.dtos;

public record LoginRequestDto(
        String email,
        String password
) {
}
