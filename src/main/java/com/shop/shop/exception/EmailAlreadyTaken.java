package com.shop.shop.exception;

public class EmailAlreadyTaken extends RuntimeException {
    public EmailAlreadyTaken(String email) {
        super("when email has already been taken : " + email);
    }
}
