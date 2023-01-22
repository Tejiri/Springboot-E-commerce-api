package com.example.store.dto;

import com.example.store.dto.AddressDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class CustomerDTO {

    @Setter
    private int id;

    @NotBlank(message = "Email cannot be blank")
    private final String email;

    @NotBlank(message = "Firstname cannot be blank")
    private final String firstName;

    @NotBlank(message = "Lastname cannot be blank")
    private final String lastName;

    @NotBlank(message = "Password cannot be blank")
    private final String password;

    @NotBlank(message = "Telephone cannot be blank")
    private final String telephone;

    @Setter
    private String token;

    @Setter
    private final AddressDTO address;
}
