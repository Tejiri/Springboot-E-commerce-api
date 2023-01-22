package com.example.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NewOrderDTO {


    private final int quantity;

    private final String note;


}
