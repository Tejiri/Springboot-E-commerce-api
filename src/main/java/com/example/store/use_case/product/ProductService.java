package com.example.store.use_case.product;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.entity.ProductCategory;
import com.example.store.factory.ENTITY_Factory;
import com.example.store.use_case.order.OrderRepository;
import com.example.store.use_case.product_category.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ENTITY_Factory entityFactory;
    private final ProductCategoryService productCategoryService;
    private final OrderRepository orderRepository;

    public Product findProductById(int productId) {
        Product product = productRepository.findById(productId).get();
        if (product != null) {
            return product;
        }
        return null;
    }

    public Product findOrCreateProduct(ProductDTO productDTO, int categoryId) {
        Product product = entityFactory.create(productDTO);
        Product productExists = productRepository.findByName(product.getName());
        ProductCategory productCategoryExists = productCategoryService.findProductCategoryById(categoryId);
        if (productCategoryExists != null) {
            if (productExists != null) {
                return productExists;
            }
            product.setProductCategory(productCategoryExists);
            return productRepository.save(product);
        }
        return null;
    }

    public String deleteProduct(int productId) {
        Product product = productRepository.findById(productId).get();
        for (int i = 0; i < product.getOrders().size(); i++) {
            orderRepository.delete(product.getOrders().get(i));
        }
        productRepository.delete(product);
        return "Product has been deleted successfully";
    }
}
