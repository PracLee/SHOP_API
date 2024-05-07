package com.shop.shop.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @EmbeddedId
    private ProductId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "category_id"))
    private CategoryId categoryId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @OrderBy("id")
    private List<Image> images = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "price"))
    private Money price;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @OrderBy("id")
    private List<ProductOption> options = new ArrayList<>();

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ProductId id() {
        return id;
    }

    public CategoryId categoryId() {
        return categoryId;
    }

    public Image image(int index) {
        return images.get(index);
    }

    public String name() {
        return name;
    }

    public Money price() {
        return price;
    }

    public List<Image> images() {
        return new ArrayList<>(images);
    }

    public int imagesSize() {
        return images.size();
    }

    public String description() {
        return description;
    }

    public int optionSize() {
        return options.size();
    }

    public ProductOption option(int i) {
        return options.get(i);
    }

    private Product() {
    }

    public Product(ProductId id,
                   CategoryId categoryId,
                   List<Image> images,
                   String name,
                   Money price,
                   List<ProductOption> options,
                   String description) {
        this.id = id;
        this.categoryId = categoryId;
        this.images = images;
        this.name = name;
        this.price = price;
        this.options = options;
        this.description = description;
    }
}
