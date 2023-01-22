package com.example.store.order_status;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.*;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderStatusControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ENTITY_Factory entityFactory;

    @Test
    void when_OrdersExistsAndGetOrderListByOrderStatus_expect_ListOfOrdersReturned() throws Exception {

        ProductDTO productDTOToUse = new ProductDTO("Food Seasoning Cube", "Maggi", 50.0, 100);

        ArrayList<Product> products = new ArrayList<>();
        ProductCategory productCategory = new ProductCategory(0, "All Kind of food items", "Groceries", products);

        Customer customer = new Customer(0, "steveijatomi@gmail.com", "Tejiri", "Ijatomi", "12345", "09015184095", "12345", null, null);

        ArrayList<Customer> customerArrayList = new ArrayList<>();

        Address address = new Address(0, 27, "Leek Road Houses", "Staffordshire", "ST4 2XQ", customerArrayList);
        customer.setAddress(address);

        orderRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
        customerRepository.deleteAll();
        driverRepository.deleteAll();

        addressRepository.save(address);
        Customer savedCustomer = customerRepository.save(customer);

        ProductCategory savedProductCategory = productCategoryRepository.save(productCategory);

        Product product = entityFactory.create(productDTOToUse);

        product.setProductCategory(savedProductCategory);

        Product savedProduct = productRepository.save(product);

        OrderStatus orderStatus = orderStatusRepository.findById(1).get();


        Order order = new Order(0, 50, "Thanks for the delivery", orderStatus, savedCustomer, null, savedProduct);

        Order savedOrder = orderRepository.save(order);

        mockMvc.perform(
                        get("http://localhost:8080/api/v1/order-status/get/" + "pending")
                                .header("Authorization", "123456")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(50))
                .andExpect(jsonPath("$[0].note").value("Thanks for the delivery"))
                .andExpect(jsonPath("$[0].orderStatus.status").value("pending"))
                .andExpect(jsonPath("$[0].customer.email").value("steveijatomi@gmail.com"))
                .andExpect(jsonPath("$[0].customer.telephone").value("09015184095"))
                .andExpect(jsonPath("$[0].product.name").value("Maggi"))
                .andExpect(jsonPath("$[0].product.description").value("Food Seasoning Cube"))
                .andExpect(jsonPath("$[0].product.price").value("50.0"));

    }
}