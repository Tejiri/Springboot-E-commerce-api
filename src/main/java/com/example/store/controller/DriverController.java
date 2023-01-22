package com.example.store.controller;

import com.example.store.dto.DriverDTO;
import com.example.store.use_case.driver.DriverService;
import com.example.store.factory.DTO_Factory;
import com.example.store.dto.OrderDTO;
import com.example.store.dto.UserCredentialsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("api/v1/driver")
public class DriverController {

    private final DriverService driverService;
    private final DTO_Factory dtoFactory;

    @GetMapping("get/orders/{driverId}")
    public List<OrderDTO> findDriverOrders(@PathVariable int driverId) {
        return dtoFactory.createOrdersDTOList(driverService.findDriverOrders(driverId));
    }

    @PostMapping("check-credentials")
    public DriverDTO checkCredentials(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        try {
            return dtoFactory.create(driverService.checkCredentials(userCredentialsDTO));
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping(path = "/logout/{driverId}")
    public String logOut(@PathVariable(name = "driverId") int driverId) {
        return driverService.clearToken(driverId);
    }
}
