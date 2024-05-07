package com.shop.shop.repositories;

import com.shop.shop.Fixtures;
import com.shop.shop.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static com.shop.shop.TestUtils.createCartLineItemOption;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CartRepositoryTest {
    @Autowired
    private CartRepository cartRepository;

    private Product product;
    private List<ProductOption> options;
    private List<ProductOptionItem> optionItems;

    private CartId cartId;

    @BeforeEach
    void setUp() {
        product = Fixtures.product("맨투맨");

        options = IntStream.range(0, product.optionSize())
                .mapToObj(product::option).toList();

        optionItems = IntStream.range(0, product.optionSize())
                .mapToObj(product::option)
                .map(option -> option.item(0))
                .toList();
    }

    @BeforeEach
    void createCart() {
        cartId = CartId.generate();

        Cart cart = new Cart(cartId, new UserId("USER-ID"));

        Set<CartLineItemOption> options = Set.of(
                createCartLineItemOption(product, 0, 0),
                createCartLineItemOption(product, 1, 0)
        );

        cart.addProduct(product, options, 3);

        cartRepository.save(cart);
    }

    @Test
    void find() {
        Cart cart = cartRepository.findById(cartId).orElseThrow();

        assertThat(cart.lineItemSize()).isEqualTo(1);

        CartLineItem lineItem = cart.lineItem(0);

        assertThat(lineItem.productId()).isEqualTo(product.id());

        assertThat(lineItem.optionSize()).isEqualTo(2);

        assertThat(lineItem.optionIds()).hasSameElementsAs(
                options.stream().map(ProductOption::id).toList());

        IntStream.range(0, options.size()).forEach(index -> {
            ProductOptionId optionId = options.get(index).id();
            ProductOptionItemId optionItemId = optionItems.get(index).id();
            assertThat(lineItem.optionItemId(optionId)).isEqualTo(optionItemId);
        });
    }
}