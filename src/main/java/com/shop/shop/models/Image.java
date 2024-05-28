package com.shop.shop.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "images")
public class Image {
    @EmbeddedId
    private ImageId id;

    @Column(name = "url")
    private String url;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Image(String url) {
        this.url = url;
    }

    public Image(ImageId id, String url) {
        this.id = id;
        this.url = url;
    }

    public String url() {
        return url;
    }

    public ImageId id() {
        return id;
    }

    public void changeUrl(String url) {
        this.url = url;
    }
}
