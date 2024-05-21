package com.shop.shop.application;

import com.shop.shop.models.Category;
import com.shop.shop.models.CategoryId;
import com.shop.shop.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateCategoryServiceTest {
    private CategoryRepository categoryRepository;

    private UpdateCategoryService updateCategoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);

        updateCategoryService = new UpdateCategoryService(categoryRepository);
    }

    @Test
    void updateCategory() {
        CategoryId id = CategoryId.generate();
        String name = "New Name";
        boolean hidden = true;

        Category category = new Category(id, "Name", !hidden);

        given(categoryRepository.findById(id))
                .willReturn(Optional.of(category));

        updateCategoryService.updateCategory(id, name, hidden);

        assertThat(category.name()).isEqualTo(name);
        assertThat(category.hidden()).isEqualTo(hidden);
    }
}