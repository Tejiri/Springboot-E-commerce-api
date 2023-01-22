//package com.example.store.supervisor;
//
//import com.example.store.factory.DTO_Factory;
//import com.example.store.factory.ENTITY_Factory;
//import com.example.store.use_case.address.AddressRepository;
//import com.example.store.use_case.customer.CustomerRepository;
//import com.example.store.use_case.driver.DriverRepository;
//import com.example.store.use_case.driver_status.DriverStatusRepository;
//import com.example.store.use_case.order.OrderRepository;
//import com.example.store.use_case.order_status.OrderStatusRepository;
//import com.example.store.use_case.product.ProductRepository;
//import com.example.store.use_case.product_category.ProductCategoryRepository;
//import com.example.store.use_case.supervisor.SupervisorRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//class SupervisorControllerIntegrationTest {
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    private AddressRepository addressRepository;
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private DriverRepository driverRepository;
//    @Autowired
//    private DriverStatusRepository driverStatusRepository;
//    @Autowired
//    private OrderRepository orderRepository;
//    @Autowired
//    private OrderStatusRepository orderStatusRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ProductCategoryRepository productCategoryRepository;
//
//    @Autowired
//    private SupervisorRepository supervisorRepository;
//
//    @Autowired
//    private ENTITY_Factory entityFactory;
//    @Autowired
//    private DTO_Factory dtoFactory;
//
//    @Test
//    void checkCredentials() {
//    }
//
//    @Test
//    void logOut() {
//    }
//}