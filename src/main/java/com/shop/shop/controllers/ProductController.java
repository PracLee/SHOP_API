package com.shop.shop.controllers;

import com.shop.shop.application.GetProductDetailService;
import com.shop.shop.application.GetProductListService;
import com.shop.shop.dtos.ProductDetailDto;
import com.shop.shop.dtos.ProductListDto;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
    private final GetProductListService getProductListService;
    private final GetProductDetailService getProductDetailService;

    public ProductController(GetProductListService getProductListService,
                             GetProductDetailService getProductDetailService) {
        this.getProductListService = getProductListService;
        this.getProductDetailService = getProductDetailService;
    }

    @GetMapping
    public ProductListDto list(
            @RequestParam(required = false) String categoryId
    ) {
        return getProductListService.getProductListDto(categoryId);
    }

    @GetMapping("/{id}")
    public ProductDetailDto detail(@PathVariable String id) {
        return getProductDetailService.getProductDetailDto(id);
    }
}
