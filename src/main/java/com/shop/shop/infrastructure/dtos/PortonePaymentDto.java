package com.shop.shop.infrastructure.dtos;

public record PortonePaymentDto(
        Response response
) {
    public record Response(
            Long amount
    ) {
    }
}