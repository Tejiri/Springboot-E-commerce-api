package com.example.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class DriverDTO {

    @Setter
    private int id;

    private final String email;

    private final String firstName;

    private final String lastName;

    @Setter
    private String password;

    @Setter
    private String token;

    @Setter
    private DriverStatusDTO driverStatus;


}
