package com.example.store.controller;

import com.example.store.factory.DTO_Factory;
import com.example.store.dto.OrderDTO;
import com.example.store.use_case.order_status.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("api/v1/order-status")
public class OrderStatusController {
    private final OrderStatusService orderStatusService;
    private final DTO_Factory dtoFactory;

    @GetMapping("get/{statusName}")
    public List<OrderDTO> getPendingOrders(@PathVariable String statusName) {
        try {
            return dtoFactory.createOrdersDTOList(orderStatusService.findListOfOrdersByStatusName(statusName));
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }

    }
}
