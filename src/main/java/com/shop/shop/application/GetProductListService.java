package com.shop.shop.application;

import com.shop.shop.dtos.AdminProductListDto;
import com.shop.shop.dtos.AdminProductSummaryDto;
import com.shop.shop.dtos.ProductListDto;
import com.shop.shop.dtos.ProductSummaryDto;
import com.shop.shop.models.Category;
import com.shop.shop.models.CategoryId;
import com.shop.shop.models.Product;
import com.shop.shop.repositories.CategoryRepository;
import com.shop.shop.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetProductListService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public GetProductListService(ProductRepository productRepository,
                                 CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductListDto getProductListDto(String categoryId) {
        List<Product> products = findProducts(categoryId);

        List<ProductSummaryDto> productSummaryDtos = products.stream()
                .map(product -> {
                    Category category = categoryRepository
                            .findById(product.categoryId())
                            .get();
                    return ProductSummaryDto.of(product, category);
                })
                .toList();

        return new ProductListDto(productSummaryDtos);
    }

    private List<Product> findProducts(String categoryId) {
        if (categoryId == null) {
            return productRepository.findAllByHiddenIsFalseOrderByIdAsc();
        }

        CategoryId id = new CategoryId(categoryId);
        return productRepository.
                findAllByCategoryIdAndHiddenIsFalseOrderByIdAsc(id);
    }

    public AdminProductListDto getAdminProductListDto() {
        List<Product> products = productRepository.findAllByOrderByIdAsc();

        List<AdminProductSummaryDto> productSummaryDtos = products.stream()
                .map(product -> {
                    Category category = categoryRepository
                            .findById(product.categoryId())
                            .orElseThrow();
                    return AdminProductSummaryDto.of(product, category);
                })
                .toList();

        return new AdminProductListDto(productSummaryDtos);
    }
}