package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class ProductDTOWithCategory {

    @Setter
    private int id;

    @NotBlank(message = "Description Number cannot be blank")
    private final String description;

    @NotBlank(message = "Name cannot be blank")
    private final String name;

    @NotBlank(message = "Price cannot be blank")
    private final Double price;

    @NotBlank(message = "Stock Quantity cannot be blank")
    private final int stockQuantity;

    @Setter
    private ProductCategoryDTO productCategory;

}
