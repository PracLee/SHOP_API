package com.shop.shop.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
public class Category {
    @EmbeddedId
    private CategoryId id;
    @Column(name = "name")
    private String name;

    public Category(CategoryId id, String name) {
        this.id = id;
        this.name = name;
    }

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Category() {

    }

    public CategoryId id() {
        return id;
    }

    public String name() {
        return name;
    }
}
