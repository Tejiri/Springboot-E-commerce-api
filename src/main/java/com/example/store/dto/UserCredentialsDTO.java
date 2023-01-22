package com.example.store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserCredentialsDTO {
    @Email(message = "Email is not in the correct format")
    private final String email;

    @NotBlank(message = "Password cannot be blank")
    private final String password;
}