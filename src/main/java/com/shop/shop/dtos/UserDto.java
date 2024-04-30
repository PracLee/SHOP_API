package com.shop.shop.dtos;

import com.shop.shop.models.User;

public record UserDto(
        String id,
        String name
) {
    public static UserDto of(User user) {
        return new UserDto(
                user.id().toString(),
                user.name()
        );
    }
}