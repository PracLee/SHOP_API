package com.shop.shop.application;

import com.shop.shop.models.Category;
import com.shop.shop.models.CategoryId;
import com.shop.shop.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetCategoryListServiceTest {
    private CategoryRepository categoryRepository;
    private GetCategoryListService getCategoryListService;

    @BeforeEach
    void setUp() {
        CategoryId id = new CategoryId("0BV000CAT0001");
        Category category = new Category(id, "top");

        categoryRepository = mock(CategoryRepository.class);

        given(categoryRepository.findAll()).willReturn(List.of(category));

        getCategoryListService = new GetCategoryListService(categoryRepository);
    }

    @Test
    void list() {
        CategoryId id = new CategoryId("0BV000CAT0001");
        Category category = new Category(id, "top");

        given(categoryRepository.findAllByHiddenIsFalseOrderByIdAsc())
                .willReturn(List.of(category));
        
        List<Category> categories = getCategoryListService.getCategories();

        assertThat(categories).hasSize(1);
    }
}