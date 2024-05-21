package com.shop.shop.application;

import com.shop.shop.models.Category;
import com.shop.shop.models.CategoryId;
import com.shop.shop.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetCategoryDetailService {
    private final CategoryRepository categoryRepository;

    public GetCategoryDetailService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategory(CategoryId categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow();
    }
}