package com.shop.shop.repositories;

import com.shop.shop.models.Category;
import com.shop.shop.models.CategoryId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, CategoryId> {
    List<Category> findAll();
}
