package com.shop.shop.application;

import com.shop.shop.Fixtures;
import com.shop.shop.dtos.CartDto;
import com.shop.shop.models.Cart;
import com.shop.shop.models.CartLineItemOption;
import com.shop.shop.models.Product;
import com.shop.shop.models.UserId;
import com.shop.shop.repositories.CartRepository;
import com.shop.shop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static com.shop.shop.TestUtils.createCartLineItemOption;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetCartServiceTest {
    private GetCartService getCartService;

    private CartRepository cartRepository;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);

        productRepository = mock(ProductRepository.class);

        getCartService = new GetCartService(cartRepository, productRepository);
    }

    @Test
    @DisplayName("getCartDto - when cart doesn't exist")
    void getCartDto() {
        UserId userId = new UserId("USER-ID");

        CartDto cartDto = getCartService.getCartDto(userId);

        assertThat(cartDto.lineItems()).isEmpty();

        assertThat(cartDto.totalPrice()).isEqualTo(0L);
    }

    @Test
    @DisplayName("getCartDto - when cart already exists")
    void getCartDtoWhenCartExists() {
        UserId userId = new UserId("USER-ID");

        Cart cart = new Cart(userId);

        Product product = Fixtures.product("맨투맨");

        Set<CartLineItemOption> options = Set.of(
                createCartLineItemOption(product, 0, 0),
                createCartLineItemOption(product, 1, 0)
        );

        cart.addProduct(product, options, 1);

        given(cartRepository.findByUserId(userId))
                .willReturn(Optional.of(cart));

        given(productRepository.findById(product.id()))
                .willReturn(Optional.of(product));

        CartDto cartDto = getCartService.getCartDto(userId);

        assertThat(cartDto.totalPrice()).isEqualTo(product.price().asLong());
    }
}