package com.shop.shop.controllers;

import com.shop.shop.application.GetCartService;
import com.shop.shop.dtos.CartDto;
import com.shop.shop.models.UserId;
import com.shop.shop.security.AuthUser;
import com.shop.shop.security.UserRequired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@UserRequired
@RestController
@RequestMapping("/cart")
public class CartController {
    private final GetCartService getCartService;

    public CartController(GetCartService getCartService) {
        this.getCartService = getCartService;
    }

    @GetMapping
    public CartDto detail(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        UserId userId = new UserId(authUser.id());

        return getCartService.getCartDto(userId);
    }
}