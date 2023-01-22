package com.example.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class OrderDTO {
    @Setter
    private int id;

    private final int quantity;

    private final String note;

    @Setter
    private  OrderStatusDTO orderStatus;

    @Setter
    private CustomerDTO customer;

    @Setter
    private DriverDTO driver;

    @Setter
    private ProductDTO product;
}
