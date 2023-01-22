package com.example.store.controller;

import com.example.store.factory.DTO_Factory;
import com.example.store.dto.ProductDTO;
import com.example.store.dto.ProductDTOWithCategory;
import com.example.store.use_case.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService productService;
    private final DTO_Factory dtoFactory;

    @PostMapping("create/{productCategoryId}")
    public ProductDTOWithCategory findOrCreateProduct(@RequestBody ProductDTO productDTO, @PathVariable int productCategoryId) {
        try {
            return dtoFactory.createProductDTOWithCategory(productService.findOrCreateProduct(productDTO, productCategoryId));
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("delete/{productId}")
    public String deleteProduct(@PathVariable int productId) {
        try {
            return productService.deleteProduct(productId);
        } catch (Exception e) {
            return e.toString();
        }
    }
}
