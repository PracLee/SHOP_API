package com.shop.shop.application;

import com.shop.shop.Fixtures;
import com.shop.shop.dtos.AdminUpdateProductDto;
import com.shop.shop.models.Product;
import com.shop.shop.models.ProductId;
import com.shop.shop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateProductServiceTest {
    private ProductRepository productRepository;

    private UpdateProductService updateProductService;

    @BeforeEach
    void setUpMockObjects() {
        productRepository = mock(ProductRepository.class);

        updateProductService = new UpdateProductService(productRepository);
    }

    @Test
    void updateProduct() {
        Product product = Fixtures.product("맨투맨");
        ProductId productId = product.id();

        given(productRepository.findById(productId))
                .willReturn(Optional.of(product));

        AdminUpdateProductDto productDto = new AdminUpdateProductDto(
                "Category-ID",
                List.of(),
                "New Name",
                product.price().asLong(),
                List.of(),
                "New Description",
                false
        );

        updateProductService.updateProduct(productId, productDto);

        assertThat(product.name()).isEqualTo("New Name");
    }
}