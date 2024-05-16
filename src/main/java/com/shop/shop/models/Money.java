package com.shop.shop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Money {
    @Column(name = "amount")
    private Long amount;

    private Money() {
    }

    public Money(Long amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    public Long asLong() {
        return amount;
    }

    public Money times(int quantity) {
        return new Money(amount * quantity);
    }

    public Money plus(Money other) {
        return new Money(amount + other.amount);
    }

    public static final Money ZERO = new Money(0L);

}
