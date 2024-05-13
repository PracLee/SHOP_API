package com.shop.shop.controllers;

import com.shop.shop.application.CreateOrderService;
import com.shop.shop.dtos.OrderRequestDto;
import com.shop.shop.models.*;
import com.shop.shop.security.AuthUser;
import com.shop.shop.security.UserRequired;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@UserRequired
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final CreateOrderService createOrderService;

    public OrderController(CreateOrderService createOrderService) {
        this.createOrderService = createOrderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(
            Authentication authentication,
            @Valid @RequestBody OrderRequestDto orderRequestDto
    ) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        UserId userId = new UserId(authUser.id());

        OrderRequestDto.ReceiverDto receiverDto = orderRequestDto.receiver();
        OrderRequestDto.PaymentDto paymentDto = orderRequestDto.payment();

        Receiver receiver = new Receiver(
                receiverDto.name(),
                new Address(
                        receiverDto.address1(),
                        receiverDto.address2(),
                        new PostalCode(receiverDto.postalCode())
                ),
                new PhoneNumber(receiverDto.phoneNumber())
        );

        Payment payment = new Payment(
                paymentDto.merchantId(),
                paymentDto.transactionId()
        );

        createOrderService.createOrder(userId, receiver, payment);

        return "Created";
    }
}