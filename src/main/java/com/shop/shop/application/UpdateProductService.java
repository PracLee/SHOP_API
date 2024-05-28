package com.shop.shop.application;

import com.shop.shop.dtos.AdminUpdateProductDto;
import com.shop.shop.models.Product;
import com.shop.shop.models.ProductId;
import com.shop.shop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateProductService {
    private final ProductRepository productRepository;

    public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void updateProduct(
            ProductId productId, AdminUpdateProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow();

        product.update(productDto);
    }
}