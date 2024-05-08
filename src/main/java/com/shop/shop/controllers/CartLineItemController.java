package com.shop.shop.controllers;

import com.shop.shop.application.AddProductToCartService;
import com.shop.shop.dtos.AddProductToCartDto;
import com.shop.shop.models.*;
import com.shop.shop.security.AuthUser;
import com.shop.shop.security.UserRequired;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@UserRequired
@RestController
@RequestMapping("/cart/line-items")
public class CartLineItemController {
    private final AddProductToCartService addProductToCartService;

    public CartLineItemController(
            AddProductToCartService addProductToCartService) {
        this.addProductToCartService = addProductToCartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(
            Authentication authentication,
            @Valid @RequestBody AddProductToCartDto requestDto) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        UserId userId = new UserId(authUser.id());
        ProductId productId = new ProductId(requestDto.productId());
        Set<CartLineItemOption> options = requestDto.options().stream()
                .map(option -> new CartLineItemOption(
                        new ProductOptionId(option.id()),
                        new ProductOptionItemId(option.itemId())
                ))
                .collect(Collectors.toSet());
        int quantity = requestDto.quantity();

        addProductToCartService.addProductToCart(
                userId, productId, options, quantity);

        return "Created";
    }
}