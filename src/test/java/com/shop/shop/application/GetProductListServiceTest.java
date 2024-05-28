package com.shop.shop.application;

import com.shop.shop.Fixtures;
import com.shop.shop.dtos.AdminProductListDto;
import com.shop.shop.models.Category;
import com.shop.shop.models.CategoryId;
import com.shop.shop.models.Product;
import com.shop.shop.repositories.CategoryRepository;
import com.shop.shop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetProductListServiceTest {
    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    private GetProductListService getProductListService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);

        getProductListService = new GetProductListService(
                productRepository, categoryRepository);
    }

    @Test
    void getAdminProductListDto() {
        Product product = Fixtures.product("맨투맨");
        CategoryId categoryId = product.categoryId();
        Category category = new Category(categoryId, "카테고리", false);

        given(productRepository.findAllByOrderByIdAsc())
                .willReturn(List.of(product));

        given(categoryRepository.findById(categoryId))
                .willReturn(Optional.of(category));

        AdminProductListDto adminProductListDto =
                getProductListService.getAdminProductListDto();

        assertThat(adminProductListDto.products()).hasSize(1);
    }
}