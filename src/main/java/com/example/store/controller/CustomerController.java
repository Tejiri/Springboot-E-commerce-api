package com.example.store.controller;

import com.example.store.dto.CustomerDTO;
import com.example.store.use_case.customer.CustomerService;
import com.example.store.dto.AddressDTO;
import com.example.store.factory.DTO_Factory;
import com.example.store.dto.UserCredentialsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final DTO_Factory dtoFactory;

    @PostMapping("create")
    public CustomerDTO findOrCreateCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            return dtoFactory.create(customerService.findOrCreateCustomer(customerDTO));
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @PutMapping("update/address/{customerId}")
    public CustomerDTO updateCustomerAddress(@RequestBody AddressDTO addressDTO, @PathVariable int customerId) {
        try {
            return dtoFactory.create(customerService.updateCustomerAddress(addressDTO, customerId));
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @DeleteMapping("delete/{customerId}")
    public String deleteCustomerAccount(@PathVariable int customerId) {
        try {
            return customerService.deleteCustomerAccount(customerId);
        } catch (Exception e) {
            return e.toString();
        }
    }

    @PostMapping("check-credentials")
    public CustomerDTO checkCredentials(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        try {
            return dtoFactory.create(customerService.checkCredentials(userCredentialsDTO));
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @PostMapping(path = "/logout/{customerId}")
    public String logOut(@PathVariable(name = "customerId") int customerId) {
        return customerService.clearToken(customerId);
    }


}
