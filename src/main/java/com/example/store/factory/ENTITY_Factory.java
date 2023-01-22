package com.example.store.factory;

import com.example.store.dto.AddressDTO;
import com.example.store.dto.CustomerDTO;
import com.example.store.dto.DriverDTO;
import com.example.store.dto.DriverStatusDTO;
import com.example.store.entity.*;
import com.example.store.dto.NewOrderDTO;
import com.example.store.dto.OrderDTO;
import com.example.store.dto.OrderStatusDTO;
import com.example.store.dto.ProductDTO;
import com.example.store.dto.ProductCategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class ENTITY_Factory {

    public Address create(AddressDTO addressDTO) {
        Address address = new Address(
                addressDTO.getId(),
                addressDTO.getHouseNumber(),
                addressDTO.getStreet(),
                addressDTO.getTown(),
                addressDTO.getPostcode(),
                null
        );

        return address;
    }

    public Customer create(CustomerDTO customerDTO) {
        Customer customer = new Customer(
                customerDTO.getId(),
                customerDTO.getEmail(),
                customerDTO.getFirstName(),
                customerDTO.getLastName(),
                customerDTO.getPassword(),
                customerDTO.getTelephone(),
                customerDTO.getToken(),
                create(customerDTO.getAddress()),
                null
        );
        return customer;
    }

    public ProductCategory create(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = new ProductCategory(
                productCategoryDTO.getId(),
                productCategoryDTO.getDescription(),
                productCategoryDTO.getName(),
                null
        );
        return productCategory;
    }

    public Product create(ProductDTO productDTO) {
        Product product = new Product(
                productDTO.getId(),
                productDTO.getDescription(),
                productDTO.getName(),
                productDTO.getPrice(),
                productDTO.getStockQuantity(),
                null, null);
        return product;
    }

    public DriverStatus create(DriverStatusDTO driverStatusDTO) {
        DriverStatus driverStatus = new DriverStatus(
                driverStatusDTO.getId(),
                driverStatusDTO.getStatus(),
                null
        );
        return driverStatus;
    }

    public Driver create(DriverDTO driverDTO) {
        Driver driver = new Driver(
                driverDTO.getId(),
                driverDTO.getEmail(),
                driverDTO.getFirstName(),
                driverDTO.getLastName(),
                driverDTO.getPassword(),
                driverDTO.getToken(),
                create(driverDTO.getDriverStatus()),
                null
        );
        return driver;
    }


    public OrderStatus create(OrderStatusDTO orderStatusDTO) {
        OrderStatus orderStatus = new OrderStatus(
                orderStatusDTO.getId(),
                orderStatusDTO.getStatus(),
                null
        );
        return orderStatus;
    }

    public Order create(OrderDTO orderDTO) {
        Order order = new Order(
                0,
                orderDTO.getQuantity(),
                orderDTO.getNote(),
                create(orderDTO.getOrderStatus()),
                create(orderDTO.getCustomer()),
                create(orderDTO.getDriver()),
                create(orderDTO.getProduct()));

        return order;
    }

    public Order create(NewOrderDTO newOrderDTO) {
        Order order = new Order(
                0,
                newOrderDTO.getQuantity(),
                newOrderDTO.getNote(),
                null,
                null,
                null,
                null);

        return order;
    }
}
