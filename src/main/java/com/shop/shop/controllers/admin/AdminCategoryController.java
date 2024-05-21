package com.shop.shop.controllers.admin;

import com.shop.shop.application.CreateCategoryService;
import com.shop.shop.application.GetCategoryDetailService;
import com.shop.shop.application.GetCategoryListService;
import com.shop.shop.application.UpdateCategoryService;
import com.shop.shop.dtos.AdminCategoryDetailDto;
import com.shop.shop.dtos.AdminCategoryListDto;
import com.shop.shop.dtos.AdminCreateCategoryDto;
import com.shop.shop.dtos.AdminUpdateCategoryDto;
import com.shop.shop.models.Category;
import com.shop.shop.models.CategoryId;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final GetCategoryListService getCategoryListService;
    private final GetCategoryDetailService getCategoryDetailService;
    private final CreateCategoryService createCategoryService;
    private final UpdateCategoryService updateCategoryService;

    public AdminCategoryController(GetCategoryListService getCategoryListService,
                                   GetCategoryDetailService getCategoryDetailService,
                                   CreateCategoryService createCategoryService,
                                   UpdateCategoryService updateCategoryService) {
        this.getCategoryListService = getCategoryListService;
        this.getCategoryDetailService = getCategoryDetailService;
        this.createCategoryService = createCategoryService;
        this.updateCategoryService = updateCategoryService;
    }

    @GetMapping
    public AdminCategoryListDto list() {
        List<Category> categories = getCategoryListService.getAllCategories();
        return AdminCategoryListDto.of(categories);
    }

    @GetMapping("/{id}")
    public AdminCategoryDetailDto detail(@PathVariable String id) {
        CategoryId categoryId = new CategoryId(id);
        Category category = getCategoryDetailService.getCategory(categoryId);
        return AdminCategoryDetailDto.of(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(
            @Valid @RequestBody AdminCreateCategoryDto categoryDto) {
        createCategoryService.createCategory(categoryDto.name());
        return "Created";
    }

    @PatchMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @RequestBody AdminUpdateCategoryDto categoryDto) {
        updateCategoryService.updateCategory(
                new CategoryId(id), categoryDto.name(), categoryDto.hidden());
        return "Updated";
    }
}
