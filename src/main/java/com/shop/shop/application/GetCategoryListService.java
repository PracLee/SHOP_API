package com.shop.shop.application;

import com.shop.shop.models.Category;
import com.shop.shop.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCategoryListService {
    private final CategoryRepository categoryRepository;

    public GetCategoryListService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAllByHiddenIsFalseOrderByIdAsc();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAllByOrderByIdAsc();
    }
}
