package com.shop.shop.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static com.shop.shop.models.Role.ROLE_ADMIN;

@Entity
@Table(name = "users")
public class User {
    @EmbeddedId
    private UserId id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String encodedPassword;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private User() {
    }

    public User(UserId id, String email, String name, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public UserId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.encodedPassword = passwordEncoder.encode(password);
    }

    public boolean isAdmin() {
        return role.equals(ROLE_ADMIN);
    }

    public String email() {
        return email;
    }

    public Role role() {
        return role;
    }
}