package com.example.store.use_case.product_category;


import com.example.store.entity.Product;
import com.example.store.entity.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategory findProductCategoryById(int categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId).get();
        if (productCategory != null) {
            return productCategory;
        }
        return null;
    }

    public List<Product> findProductsWithCategory(int id) {
        ProductCategory productCategory = productCategoryRepository.findById(id).get();
        if (productCategory != null) {
            return productCategory.getProducts();
        }
        return null;
    }

}
