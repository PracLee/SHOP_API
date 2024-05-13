package com.shop.shop.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @EmbeddedId
    private OrderId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private UserId userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    @OrderBy("id")
    private List<OrderLineItem> lineItems = new ArrayList<>();

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_price"))
    private Money totalPrice = Money.ZERO;

    @Embedded
    private Receiver receiver;

    @Embedded
    private Payment payment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "ordered_at")
    private LocalDateTime orderedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Order() {
    }

    public Order(OrderId id, UserId userId, List<OrderLineItem> lineItems,
                 Receiver receiver, Payment payment, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.lineItems = lineItems;
        this.totalPrice = lineItems.stream()
                .map(OrderLineItem::totalPrice)
                .reduce(Money::plus)
                .orElseThrow();
        this.receiver = receiver;
        this.payment = payment;
        this.status = status;
        this.orderedAt = LocalDateTime.now();
    }

    public OrderId id() {
        return id;
    }

    public String title() {
        OrderLineItem lineItem = lineItems.get(0);
        int size = lineItems.size();

        String title = lineItem.productName();

        if (size > 1) {
            return title + " 외 " + (size - 1);
        }

        return title;
    }

    public int lineItemSize() {
        return lineItems.size();
    }

    public OrderLineItem lineItem(int index) {
        return lineItems.get(index);
    }

    public Money totalPrice() {
        return totalPrice;
    }

    public OrderStatus status() {
        return status;
    }

    public LocalDateTime orderedAt() {
        return orderedAt;
    }
}