package com.example.store.customer;

import com.example.store.dto.AddressDTO;
import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.entity.ProductCategory;
import com.example.store.use_case.address.AddressRepository;
import com.example.store.use_case.customer.CustomerRepository;
import com.example.store.use_case.driver.DriverRepository;
import com.example.store.use_case.driver_status.DriverStatusRepository;
import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Address;
import com.example.store.entity.Customer;
import com.example.store.factory.DTO_Factory;
import com.example.store.factory.ENTITY_Factory;
import com.example.store.use_case.order.OrderRepository;
import com.example.store.use_case.order_status.OrderStatusRepository;
import com.example.store.use_case.product.ProductRepository;
import com.example.store.use_case.product_category.ProductCategoryRepository;
import com.example.store.use_case.supervisor.SupervisorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DTO_Factory dtoFactory;

    @Test
    public void when_CustomerExistsAndCreateCustomerAccount_expect_PreviousDetailsReturned() throws Exception {
        Customer customer = new Customer(0, "steveijatomi@gmail.com", "Tejiri", "Ijatomi", "12345", "090151518", "12345", null, null);

        ArrayList<Customer> customerArrayList =new ArrayList<>();

        Address address = new Address(0, 27, "Leek Road Houses", "Staffordshire", "ST4 2XQ", customerArrayList);
        customer.setAddress(address);

        orderRepository.deleteAll();
        customerRepository.deleteAll();
        addressRepository.deleteAll();

        addressRepository.save(address);
        customerRepository.save(customer);


        CustomerDTO expectedResponse = dtoFactory.create(customer);

        String expectedJson = new ObjectMapper().writeValueAsString(expectedResponse);


        AddressDTO addressDTO = new AddressDTO(
                10,
                "ds",
                "disj",
                "ddsin"
        );
        CustomerDTO customerToCreate = new CustomerDTO(

                "steveijatomi@gmail.com",
                "firstName",
                "firstName",
                "lastName",
                "dsdnds",
                addressDTO);

        String customerToCreateJson = new ObjectMapper().writeValueAsString(customerToCreate);
        mockMvc.perform(
                        post("http://localhost:8080/api/v1/customer/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(customerToCreateJson)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }

    @Test
    public void when_CustomerExistsAndDeleteCustomerAccount_expect_SuccessStringReturned() throws Exception {
        Customer customer = new Customer(0, "steveijatomi@gmail.com", "Tejiri", "Ijatomi", "12345", "090151518", "12345", null, null);

        ArrayList<Customer> customerArrayList =new ArrayList<>();

        Address address = new Address(0, 27, "Leek Road Houses", "Staffordshire", "ST4 2XQ", customerArrayList);
        customer.setAddress(address);

        orderRepository.deleteAll();
        customerRepository.deleteAll();
        addressRepository.deleteAll();

        addressRepository.save(address);
        Customer customerSaved = customerRepository.save(customer);

        AddressDTO addressDTO = new AddressDTO(
                10,
                "ds",
                "disj",
                "ddsin"
        );

        mockMvc.perform(
                        delete("http://localhost:8080/api/v1/customer/delete/" + customerSaved.getId())
                                .header("Authorization", "12345")
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().string("Customer Account has been deleted successfully"));
    }

    @Test
    public void when_CustomerExistsAndUpdateCustomerAddress_expect_CustomerUpdatedInformationReturned() throws Exception {
        Customer customer = new Customer(0, "steveijatomi@gmail.com", "Tejiri", "Ijatomi", "12345", "090151518", "12345", null, null);

        ArrayList<Customer> customerArrayList =new ArrayList<>();
        Address address = new Address(0, 27, "Leek Road Houses", "Staffordshire", "ST4 2XQ", customerArrayList);
        customer.setAddress(address);

        orderRepository.deleteAll();
        customerRepository.deleteAll();
        addressRepository.deleteAll();

        addressRepository.save(address);
        Customer customerSaved = customerRepository.save(customer);

        AddressDTO newAddressDTO = new AddressDTO(
                27,
                "27 Leek Road",
                "Staffordshire ",
                "ST4 2XQ"
        );

        newAddressDTO.setId(customerSaved.getAddress().getId());

        CustomerDTO expectedCustomerDTO = new CustomerDTO("steveijatomi@gmail.com", "Tejiri", "Ijatomi", "12345", "090151518", newAddressDTO);
        expectedCustomerDTO.setToken("12345");
        expectedCustomerDTO.setId(customerSaved.getId());
        String newAddressJson = new ObjectMapper().writeValueAsString(newAddressDTO);
        String expectedCustomerJson = new ObjectMapper().writeValueAsString(expectedCustomerDTO);
        mockMvc.perform(
                        put("http://localhost:8080/api/v1/customer/update/address/" + customerSaved.getId())
                                .header("Authorization", "12345")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(newAddressJson)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().string(expectedCustomerJson));
    }

}