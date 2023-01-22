package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class AddressDTO {

    @Setter
    private int id;

    @NotBlank(message = "House Number cannot be blank")
    private final int houseNumber;

    @NotBlank(message = "Street cannot be blank")
    private final String street;

    @NotBlank(message = "Town cannot be blank")
    private final String town;

    @NotBlank(message = "Postcode cannot be blank")
    private final String postcode;

}
