package com.shop.shop.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product_options")
public class ProductOption {
    @EmbeddedId
    private ProductOptionId id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_option_id")
    @OrderBy("id")
    private List<ProductOptionItem> items = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ProductOptionId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public int itemSize() {
        return items.size();
    }

    public ProductOptionItem item(int i) {
        return items.get(i);
    }

    private ProductOption() {
    }

    public ProductOption(ProductOptionId id, String name, List<ProductOptionItem> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public ProductOptionItem itemById(ProductOptionItemId itemId) {
        return items.stream()
                .filter(item -> Objects.equals(item.id(), itemId))
                .findFirst()
                .orElseThrow();
    }
}
