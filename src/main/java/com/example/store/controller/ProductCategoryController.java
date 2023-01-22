package com.example.store.controller;

import com.example.store.factory.DTO_Factory;
import com.example.store.dto.ProductDTO;
import com.example.store.use_case.product_category.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("api/v1/product-category")
public class ProductCategoryController {

    private final DTO_Factory dtoFactory;
    private final ProductCategoryService productCategoryService;

    @GetMapping("get/{productCategoryId}")
    public List<ProductDTO> getProductsWithCategoryUsingId(@PathVariable int productCategoryId) {
        try {
            return dtoFactory.create(productCategoryService.findProductsWithCategory(productCategoryId));
        } catch (Exception e) {
            return null;
        }

    }
}
