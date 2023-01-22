package com.example.store.order;

import com.example.store.dto.NewOrderDTO;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private DriverStatusRepository driverStatusRepository;
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
    void when_CreateOrder_expect_OrderReturned() throws Exception {

        NewOrderDTO newOrderDTO = new NewOrderDTO(50, "Thanks for the delivery");

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

        addressRepository.save(address);
        Customer savedCustomer = customerRepository.save(customer);

        ProductCategory savedProductCategory = productCategoryRepository.save(productCategory);

        Product product = entityFactory.create(productDTOToUse);

        product.setProductCategory(savedProductCategory);

        Product savedProduct = productRepository.save(product);

        String orderToCreate = new ObjectMapper().writeValueAsString(newOrderDTO);

        mockMvc.perform(
                        post("http://localhost:8080/api/v1/order/create/" + savedProduct.getId() + "/" + savedCustomer.getId())
                                .header("Authorization", "12345")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(orderToCreate)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(50))
                .andExpect(jsonPath("$.note").value("Thanks for the delivery"))
                .andExpect(jsonPath("$.orderStatus.status").value("pending"))
                .andExpect(jsonPath("$.customer.email").value("steveijatomi@gmail.com"))
                .andExpect(jsonPath("$.customer.telephone").value("09015184095"))
                .andExpect(jsonPath("$.product.name").value("Maggi"))
                .andExpect(jsonPath("$.product.description").value("Food Seasoning Cube"))
                .andExpect(jsonPath("$.product.price").value("50.0"));

    }

    @Test
    void when_OrderExistsAndDeleteOrder_expect_SuccessStringReturned() throws Exception {


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
                        delete("http://localhost:8080/api/v1/order/delete/" + savedOrder.getId() + "/" + savedCustomer.getId())
                                .header("Authorization", "12345")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Order has been deleted successfully"));

    }


    @Test
    void when_OrderExistsAndUpdateOrderDriver_expect_UpdatedOrderReturned() throws Exception {

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


        DriverStatus driverStatus = driverStatusRepository.findById(1).get();
        Driver driver = new Driver(0, "test-driver@mail.com", "test-firstname", "test-lastname", "12345", "1234567", driverStatus, null);

        Driver savedDriver = driverRepository.save(driver);

        mockMvc.perform(
                        put("http://localhost:8080/api/v1/order/update/driver/" + savedOrder.getId() + "/" + savedDriver.getId())
                                .header("Authorization", "123456")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driver.email").value("test-driver@mail.com"))
                .andExpect(jsonPath("$.driver.firstName").value("test-firstname"))
                .andExpect(jsonPath("$.driver.lastName").value("test-lastname"));

    }


    @Test
    void when_OrderExistsAndUpdateOrderStatus_expect_UpdatedOrderReturned() throws Exception {

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
        DriverStatus driverStatus = driverStatusRepository.findById(1).get();
        ArrayList<Order> orders = new ArrayList<>();
        Driver driver = new Driver(0, "test-driver@mail.com", "test-firstname", "test-lastname", "12345", "1234567", driverStatus, orders);

        Driver savedDriver = driverRepository.save(driver);

        Order order = new Order(0, 50, "Thanks for the delivery", orderStatus, savedCustomer, savedDriver, savedProduct);

        Order savedOrder = orderRepository.save(order);

        mockMvc.perform(
                        put("http://localhost:8080/api/v1/order/update/status/" + savedOrder.getId() + "/" + "processing" +"/"+ savedDriver.getId())
                                .header("Authorization", "1234567")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus.id").value(2))
                .andExpect(jsonPath("$.orderStatus.status").value("processing"));
    }

}