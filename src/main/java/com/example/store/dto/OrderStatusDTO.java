package com.example.store.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class OrderStatusDTO {

    @Setter
    private int id;

    @NotBlank(message = "Status cannot be blank")
    private final String status;
}
