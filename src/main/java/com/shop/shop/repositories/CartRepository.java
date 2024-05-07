package com.shop.shop.repositories;

import com.shop.shop.models.Cart;
import com.shop.shop.models.CartId;
import com.shop.shop.models.UserId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, CartId> {
    Optional<Cart> findByUserId(UserId userId);
}