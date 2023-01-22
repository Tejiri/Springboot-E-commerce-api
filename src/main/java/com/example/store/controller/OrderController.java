package com.example.store.controller;

import com.example.store.dto.NewOrderDTO;
import com.example.store.dto.OrderDTO;
import com.example.store.factory.DTO_Factory;
import com.example.store.use_case.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService;
    private final DTO_Factory dtoFactory;

    @PostMapping("create/{productId}/{customerId}")
    public OrderDTO createOrder(@RequestBody NewOrderDTO newOrderDTO, @PathVariable int productId, @PathVariable int customerId) {
        try {
            return dtoFactory.create(orderService.createOrder(newOrderDTO, productId, customerId));
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @PutMapping("update/driver/{orderId}/{driverId}")
    public OrderDTO updateOrderDriver(@PathVariable int orderId, @PathVariable int driverId) {
        try {
            return dtoFactory.create(orderService.updateOrderDriver(orderId, driverId));
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @PutMapping("update/status/{orderId}/{statusName}/{driverId}")
    public OrderDTO updateOrderStatus(@PathVariable int orderId, @PathVariable String statusName,@PathVariable int driverId) {
        try {
            return dtoFactory.create(orderService.updateOrderStatus(orderId, statusName));
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @DeleteMapping("delete/{orderId}/{customerId}")
    public String deleteOrder(@PathVariable int orderId,@PathVariable int customerId) {
        try {
            return orderService.deleteOrder(orderId,customerId);
        } catch (Exception e) {
            return e.toString();
        }
    }
}
