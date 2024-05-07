package com.shop.shop.application;

import com.shop.shop.models.*;
import com.shop.shop.repositories.CartRepository;
import com.shop.shop.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AddProductToCartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public AddProductToCartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public void addProductToCart(UserId userId, ProductId productId,
                                 Set<CartLineItemOption> options,
                                 int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(CartId.generate(), userId));
        Product product = productRepository.findById(productId)
                .orElseThrow();

        cart.addProduct(product, options, quantity);

        cartRepository.save(cart);
    }
}
