package com.example.store.product_category;

import com.example.store.entity.Product;
import com.example.store.entity.ProductCategory;
import com.example.store.factory.DTO_Factory;
import com.example.store.factory.ENTITY_Factory;
import com.example.store.use_case.address.AddressRepository;
import com.example.store.use_case.customer.CustomerRepository;
import com.example.store.use_case.driver.DriverRepository;
import com.example.store.use_case.driver_status.DriverStatusRepository;
import com.example.store.use_case.order.OrderRepository;
import com.example.store.use_case.order_status.OrderStatusRepository;
import com.example.store.use_case.product.ProductRepository;
import com.example.store.use_case.product_category.ProductCategoryRepository;
import com.example.store.use_case.supervisor.SupervisorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductCategoryControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void when_ProductsExistsByCategory_expect_ProductsListReturned() throws Exception {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();

        ArrayList<Product> products = new ArrayList<>();
        ProductCategory productCategory = new ProductCategory(0, "Set of Groceries", "Groceries", products);

        ProductCategory createdCategory = productCategoryRepository.save(productCategory);

        Product product = new Product(
                0,
                "Good for cooking stew",
                "Tomato Passata",
                20.0,
                100,
                createdCategory,
                null
        );

        Product createdProduct = productRepository.save(product);

        mockMvc.perform(
                        get("http://localhost:8080/api/v1/product-category/get/" + createdCategory.getId())
                                .header("AUTHORIZATION", "12345")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(createdProduct.getId()))
                .andExpect(jsonPath("$[0].description").value("Good for cooking stew"))
                .andExpect(jsonPath("$[0].name").value("Tomato Passata"))
                .andExpect(jsonPath("$[0].price").value(20.0))
                .andExpect(jsonPath("$[0].stockQuantity").value(100));
    }

}