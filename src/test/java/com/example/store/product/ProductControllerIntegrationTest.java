package com.example.store.product;

import com.example.store.dto.AddressDTO;
import com.example.store.dto.CustomerDTO;
import com.example.store.dto.ProductCategoryDTO;
import com.example.store.dto.ProductDTO;
import com.example.store.entity.Address;
import com.example.store.entity.Customer;
import com.example.store.entity.Product;
import com.example.store.entity.ProductCategory;
import com.example.store.factory.DTO_Factory;
import com.example.store.factory.ENTITY_Factory;
import com.example.store.use_case.order.OrderRepository;
import com.example.store.use_case.product.ProductRepository;
import com.example.store.use_case.product_category.ProductCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    ENTITY_Factory entityFactory;
    @Autowired
    DTO_Factory dtoFactory;


    @Test
    public void when_ProductDoesNotExistAndCreateProduct_expect_ProductDetailsReturned() throws Exception {

        ProductDTO productDTOToUse = new ProductDTO("Food Seasoning Cube", "Maggi", 50.0, 100);

        ArrayList<Product> products = new ArrayList<>();
        ProductCategory productCategory = new ProductCategory(0, "All Kind of food items", "Groceries", products);

        orderRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();

        ProductCategory savedProductCategory = productCategoryRepository.save(productCategory);

        String productToCreateJson = new ObjectMapper().writeValueAsString(productDTOToUse);

        mockMvc.perform(
                        post("http://localhost:8080/api/v1/product/create/" + savedProductCategory.getId())
                                .header("Authorization", "123456")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(productToCreateJson)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maggi"))
                .andExpect(jsonPath("$.description").value("Food Seasoning Cube"))
                .andExpect(jsonPath("$.price").value(50.0))
                .andExpect(jsonPath("$.stockQuantity").value(100))
                .andExpect(jsonPath("$.productCategory.name").value("Groceries"))
                .andExpect(jsonPath("$.productCategory.description").value("All Kind of food items"));
    }

    @Test
    public void when_ProductExistAndDeleteProduct_expect_SuccessMessageReturned() throws Exception {

        ProductDTO productDTOToUse = new ProductDTO("Food Seasoning Cube", "Maggi", 50.0, 100);

        ArrayList<Product> products = new ArrayList<>();
        ProductCategory productCategory = new ProductCategory(0, "All Kind of food items", "Groceries", products);

        orderRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();

        ProductCategory savedProductCategory = productCategoryRepository.save(productCategory);

        Product product = entityFactory.create(productDTOToUse);

        product.setProductCategory(savedProductCategory);

        Product savedProduct = productRepository.save(product);

        mockMvc.perform(
                        delete("http://localhost:8080/api/v1/product/delete/" + savedProduct.getId())
                                .header("Authorization", "123456")
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().string("Product has been deleted successfully"));
    }

}