package com.example.store.use_case.customer;

import com.example.store.dto.AddressDTO;
import com.example.store.use_case.address.AddressService;
import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Address;
import com.example.store.entity.Customer;
import com.example.store.factory.ENTITY_Factory;
import com.example.store.use_case.order.OrderRepository;
import com.example.store.dto.UserCredentialsDTO;
import com.example.store.utils.StringHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final AddressService addressService;
    private final StringHasher stringHasher;
    private final ENTITY_Factory entityFactory;

    public Customer findCustomerById(int customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        if (customer != null) {
            return customer;
        }
        return null;
    }

    public Customer findOrCreateCustomer(CustomerDTO customerDTO) {
        Customer customer = entityFactory.create(customerDTO);
        customer.setPassword(stringHasher.hashString(customer.getPassword()));

        Address address = addressService.findUpdateOrCreateAddress(customer.getAddress());
        customer.setAddress(address);

        Customer customerExists = customerRepository.findByEmail(customer.getEmail());
        if (customerExists != null) {
            return customerExists;
        }
        return customerRepository.save(customer);

    }

    public Customer updateCustomerAddress(AddressDTO addressDTO, int i) {
        Customer customer = customerRepository.findById(i).get();
        if (customer != null) {
            Address address = addressService.findUpdateOrCreateAddress(entityFactory.create(addressDTO));
            customer.setAddress(address);
            return customerRepository.save(customer);
        }
        return null;
    }

    public String deleteCustomerAccount(int customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        for (int i = 0; i < customer.getOrders().size(); i++) {
            orderRepository.delete(customer.getOrders().get(i));
        }
        customerRepository.delete(customer);
        return "Customer Account has been deleted successfully";
    }

    public Customer checkCredentials(UserCredentialsDTO userCredentialsDTO) {
        Customer customer = customerRepository.findByEmail(userCredentialsDTO.getEmail());

        if (customer != null && (customer.getPassword().equals(stringHasher.hashString(userCredentialsDTO.getPassword())) || customer.getPassword().equals(userCredentialsDTO.getPassword()))) {
            String token = customer.getEmail() + ":" + LocalDate.now().toString();
            customer.setToken(stringHasher.hashString(token));
            customer = customerRepository.save(customer);
            return customer;
        }
        return null;
    }

    public Customer checkCredentials(String token) {
        Customer customer = customerRepository.findByToken(token);
        if (customer != null && customer.getToken() != null) {
            return customer;
        }
        return null;
    }

    public String clearToken(int id) {
        Customer customer = customerRepository.findById(id).get();
        if (customer != null) {
            customer.setToken(null);
            customerRepository.save(customer);
            return "Token cleared and value set to null";
        }
        return "Unable to clear token";
    }

}
