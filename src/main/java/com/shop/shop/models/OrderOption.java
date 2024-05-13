package com.shop.shop.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_options")
public class OrderOption {
    @EmbeddedId
    private OrderOptionId id;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "product_option_id")
    )
    private ProductOptionId productOptionId;

    @Column(name = "name")
    private String name;

    @Embedded
    private OrderOptionItem optionItem;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private OrderOption() {
    }

    public OrderOption(OrderOptionId id, ProductOption productOption,
                       ProductOptionItem productOptionItem) {
        this.id = id;
        this.productOptionId = productOption.id();
        this.name = productOption.name();
        this.optionItem = new OrderOptionItem(productOptionItem);
    }

    public static OrderOption of(ProductOption productOption,
                                 ProductOptionItem productOptionItem) {
        OrderOptionId id = OrderOptionId.generate();
        return new OrderOption(id, productOption, productOptionItem);
    }

    public String name() {
        return name;
    }

    public OrderOptionItem optionItem() {
        return optionItem;
    }
}