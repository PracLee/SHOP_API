package com.shop.shop.application;

import com.shop.shop.Fixtures;
import com.shop.shop.dtos.AdminProductDetailDto;
import com.shop.shop.models.Category;
import com.shop.shop.models.CategoryId;
import com.shop.shop.models.Product;
import com.shop.shop.models.ProductId;
import com.shop.shop.repositories.CategoryRepository;
import com.shop.shop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetProductDetailServiceTest {
    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    private GetProductDetailService getProductDeailService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);

        getProductDeailService = new GetProductDetailService(
                productRepository, categoryRepository);
    }

    @Test
    void getAdminProductDetailDto() {
        Product product = Fixtures.product("맨투맨");
        ProductId productId = product.id();

        CategoryId categoryId = product.categoryId();
        Category category = new Category(categoryId, "카테고리", false);

        given(productRepository.findById(productId))
                .willReturn(Optional.of(product));

        given(categoryRepository.findById(categoryId))
                .willReturn(Optional.of(category));

        AdminProductDetailDto productDto =
                getProductDeailService.getAdminProductDetailDto(productId);

        assertThat(productDto.name()).isEqualTo("맨투맨");
    }
}