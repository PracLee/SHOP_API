package com.shop.shop.application;

import com.shop.shop.models.Category;
import com.shop.shop.models.CategoryId;
import com.shop.shop.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateCategoryService {
    private final CategoryRepository categoryRepository;

    public CreateCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(String name) {
        Category category = new Category(CategoryId.generate(), name, true);

        categoryRepository.save(category);
    }
}