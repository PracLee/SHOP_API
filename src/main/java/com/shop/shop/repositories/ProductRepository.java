package com.shop.shop.repositories;

import com.shop.shop.models.CategoryId;
import com.shop.shop.models.Product;
import com.shop.shop.models.ProductId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ProductRepository extends CrudRepository<Product, ProductId> {
    public List<Product> findAll();

    List<Product> findAllByCategoryId(CategoryId categoryId);
}
