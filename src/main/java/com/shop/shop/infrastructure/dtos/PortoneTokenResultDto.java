package com.shop.shop.infrastructure.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PortoneTokenResultDto(
        Response response
) {
    public record Response(
            @JsonProperty("access_token")
            String accessToken
    ) {
    }
}