package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
@Getter
public class ProductCategoryDTO {
    @Setter
    private int id;

    @NotBlank(message = "Description Number cannot be blank")
    private final String description;

    @NotBlank(message = "Description Number cannot be blank")
    private final String name;

}
