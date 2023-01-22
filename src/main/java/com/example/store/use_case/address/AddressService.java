package com.example.store.use_case.address;

import com.example.store.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address findUpdateOrCreateAddress(Address address) {
        Address addressExists = addressRepository.findByHouseNumberAndPostcode(address.getHouseNumber(), address.getPostcode());
        if (addressExists != null) {
            addressExists.setTown(address.getTown());
            addressExists.setStreet(address.getStreet());
            return addressRepository.save(addressExists);
        }
        return addressRepository.save(address);
    }
}
