package com.example.store.factory;

import com.example.store.dto.AddressDTO;
import com.example.store.dto.CustomerDTO;
import com.example.store.dto.DriverDTO;
import com.example.store.dto.DriverStatusDTO;
import com.example.store.entity.*;
import com.example.store.dto.OrderDTO;
import com.example.store.dto.OrderStatusDTO;
import com.example.store.dto.ProductDTO;
import com.example.store.dto.ProductDTOWithCategory;
import com.example.store.dto.ProductCategoryDTO;
import com.example.store.dto.SupervisorDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTO_Factory {

    public AddressDTO create(Address address) {
        AddressDTO addressDTO = new AddressDTO(
                address.getHouseNumber(),
                address.getStreet(),
                address.getTown(),
                address.getPostcode()
        );
        addressDTO.setId(address.getId());
        return addressDTO;
    }

    public CustomerDTO create(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO(
                customer.getEmail(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPassword(),
                customer.getTelephone(),
                create(customer.getAddress())
        );
        customerDTO.setId(customer.getId());
        if (customer.getToken() != null) {
            customerDTO.setToken(customer.getToken());
        }

        return customerDTO;
    }

    public ProductCategoryDTO create(ProductCategory productCategory) {
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(
                productCategory.getDescription(),
                productCategory.getName()
        );
        productCategoryDTO.setId(productCategory.getId());

        return productCategoryDTO;
    }

    public ProductDTO create(Product product) {
        ProductDTO productDTO = new ProductDTO(
                product.getDescription(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity()
        );
        productDTO.setId(product.getId());
        return productDTO;
    }

    public ProductDTOWithCategory createProductDTOWithCategory(Product product) {
        ProductDTOWithCategory productDTO = new ProductDTOWithCategory(
                product.getDescription(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity()
        );
        productDTO.setId(product.getId());
        productDTO.setProductCategory(create(product.getProductCategory()));

        return productDTO;
    }

    public List<ProductDTO> create(List<Product> products) {
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            ProductDTO productDTO = create(products.get(i));
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }


    public DriverStatusDTO create(DriverStatus driverStatus) {
        DriverStatusDTO driverStatusDTO = new DriverStatusDTO(
                driverStatus.getStatus()
        );
        driverStatusDTO.setId(driverStatus.getId());
        return driverStatusDTO;
    }

    public DriverDTO create(Driver driver) {

        DriverDTO driverDTO = new DriverDTO(
                driver.getEmail(),
                driver.getFirstName(),
                driver.getLastName()
        );
        driverDTO.setId(driver.getId());
        driverDTO.setDriverStatus(create(driver.getDriverStatus()));
        driverDTO.setPassword(driver.getPassword());
        if (driver.getToken() != null) {
            driverDTO.setToken(driver.getToken());
        }

        return driverDTO;
    }

    public SupervisorDTO create(Supervisor supervisor) {

        SupervisorDTO supervisorDTO = new SupervisorDTO(
                supervisor.getEmail(),
                supervisor.getFirstName(),
                supervisor.getLastName()
        );
        supervisorDTO.setId(supervisor.getId());
        supervisorDTO.setPassword(supervisor.getPassword());
        if (supervisor.getToken() != null) {
            supervisorDTO.setToken(supervisor.getToken());
        }
        return supervisorDTO;
    }


    public OrderStatusDTO create(OrderStatus orderStatus) {
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO(
                orderStatus.getStatus()
        );
        orderStatusDTO.setId(orderStatus.getId());
        return orderStatusDTO;
    }

    public OrderDTO create(Order order) {
        OrderDTO orderDTO = new OrderDTO(
                order.getQuantity(),
                order.getNote()
        );
        orderDTO.setCustomer(create(order.getCustomer()));
        orderDTO.setProduct(create(order.getProduct()));
        orderDTO.setOrderStatus(create(order.getOrderStatus()));
        if (order.getDriver() != null) {
            orderDTO.setDriver(create(order.getDriver()));
        }

        orderDTO.setId(order.getId());

        return orderDTO;
    }

    public List<OrderDTO> createOrdersDTOList(List<Order> orders) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {

            OrderDTO orderDTO = create(orders.get(i));
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

}
