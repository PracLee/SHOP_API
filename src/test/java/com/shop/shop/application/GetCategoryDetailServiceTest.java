package com.shop.shop.application;

import com.shop.shop.models.Category;
import com.shop.shop.models.CategoryId;
import com.shop.shop.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetCategoryDetailServiceTest {
    private CategoryRepository categoryRepository;
    private GetCategoryDetailService getCategoryDetailService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        getCategoryDetailService =
                new GetCategoryDetailService(categoryRepository);
    }

    @Test
    @DisplayName("getCategory")
    void getCategory() {
        CategoryId id = CategoryId.generate();
        given(categoryRepository.findById(id))
                .willReturn(Optional.of(new Category(id, "top")));

        Category category = getCategoryDetailService.getCategory(id);
        assertThat(category.name()).isEqualTo("top");
    }
}