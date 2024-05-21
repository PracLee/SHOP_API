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
    @Column(name = "hidden")
    private boolean hidden;

    private Category() {
    }

    public Category(CategoryId id, String name, boolean hidden) {
        this.id = id;
        this.name = name;
        this.hidden = hidden;
    }

    public Category(CategoryId id, String name) {
        this(id, name, false);
    }

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public CategoryId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public boolean hidden() {
        return hidden;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void hide() {
        this.hidden = true;
    }

    public void show() {
        this.hidden = false;
    }
}
