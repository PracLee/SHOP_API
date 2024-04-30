package com.shop.shop.controllers;

import com.shop.shop.application.GetCategoryListService;
import com.shop.shop.dtos.CategoryDto;
import com.shop.shop.dtos.CategoryListDto;
import com.shop.shop.models.Category;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final GetCategoryListService getCategoryListService;

    public CategoryController(GetCategoryListService getCategoryListService) {
        this.getCategoryListService = getCategoryListService;
    }

    @GetMapping
    public CategoryListDto list() {
        List<Category> categories = getCategoryListService.getCategories();
        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> mapToDto(category))
                .toList();

        return new CategoryListDto(categoryDtos);
    }

    private CategoryDto mapToDto(Category category) {
        return new CategoryDto(
                category.id().toString(),
                category.name()
        );
    }
}
