package com.shop.shop.controllers.admin;

import com.shop.shop.annotations.AdminRequired;
import com.shop.shop.application.CreateProductService;
import com.shop.shop.application.GetProductDetailService;
import com.shop.shop.application.GetProductListService;
import com.shop.shop.application.UpdateProductService;
import com.shop.shop.dtos.AdminCreateProductDto;
import com.shop.shop.dtos.AdminProductDetailDto;
import com.shop.shop.dtos.AdminProductListDto;
import com.shop.shop.dtos.AdminUpdateProductDto;
import com.shop.shop.models.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AdminRequired
@RestController
@RequestMapping("/admin/products")
public class AdminProductController {
    private final GetProductListService getProductListService;
    private final GetProductDetailService getProductDetailService;
    private final CreateProductService createProductService;
    private final UpdateProductService updateProductService;

    public AdminProductController(GetProductListService getProductListService,
                                  GetProductDetailService getProductDetailService,
                                  CreateProductService createProductService,
                                  UpdateProductService updateProductService) {
        this.getProductListService = getProductListService;
        this.getProductDetailService = getProductDetailService;
        this.createProductService = createProductService;
        this.updateProductService = updateProductService;
    }

    @GetMapping
    public AdminProductListDto list() {
        return getProductListService.getAdminProductListDto();
    }

    @GetMapping("/{id}")
    public AdminProductDetailDto detail(@PathVariable String id) {
        ProductId productId = new ProductId(id);
        return getProductDetailService.getAdminProductDetailDto(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody AdminCreateProductDto productDto) {
        createProductService.createProduct(
                new CategoryId(productDto.categoryId()),
                mapToImages(productDto.images()),
                productDto.name(),
                new Money(productDto.price()),
                mapToProductOptions(productDto.options()),
                productDto.description()
        );
        return "Created";
    }

    @PatchMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @RequestBody AdminUpdateProductDto productDto) {
        updateProductService.updateProduct(new ProductId(id), productDto);
        return "Updated";
    }

    private List<Image> mapToImages(
            List<AdminCreateProductDto.ImageDto> imageDtos) {
        return imageDtos.stream()
                .map(image -> new Image(ImageId.generate(), image.url()))
                .toList();
    }

    private List<ProductOption> mapToProductOptions(
            List<AdminCreateProductDto.OptionDto> optionDtos) {
        return optionDtos.stream()
                .map(option -> new ProductOption(
                        ProductOptionId.generate(),
                        option.name(),
                        mapToProductOptionItems(option.items())
                ))
                .toList();
    }

    private List<ProductOptionItem> mapToProductOptionItems(
            List<AdminCreateProductDto.OptionItemDto> optionItemDtos) {
        return optionItemDtos.stream()
                .map(optionItem -> new ProductOptionItem(
                        ProductOptionItemId.generate(),
                        optionItem.name()
                ))
                .toList();
    }
}