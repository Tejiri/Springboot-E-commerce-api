package com.example.store.use_case.driver;

import com.example.store.entity.Driver;
import com.example.store.entity.Order;
import com.example.store.dto.UserCredentialsDTO;
import com.example.store.utils.StringHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final StringHasher stringHasher;

    public Driver findDriverById(int id) {
        Driver driver = driverRepository.findById(id).get();
        if (driver != null) {
            return driver;
        }
        return null;
    }

    public List<Order> findDriverOrders(int driverId) {
        Driver driver = driverRepository.findById(driverId).get();
        if (driver != null) {
            return driver.getOrders();
        }
        return null;
    }


    public Driver checkCredentials(UserCredentialsDTO userCredentialsDTO) {
        Driver driver = driverRepository.findByEmail(userCredentialsDTO.getEmail());
        if (driver != null && (driver.getPassword().equals(userCredentialsDTO.getPassword()) || driver.getPassword().equals(userCredentialsDTO.getPassword()))) {
            String token = stringHasher.hashString(driver.getEmail() + ":" + LocalDate.now().toString());
            driver.setToken(token);
            driver = driverRepository.save(driver);
            return driver;
        }
        return null;
    }

    public Driver checkCredentials(String token) {
        Driver driver = driverRepository.findByToken(token);
        if (driver != null && driver.getToken() != null) {
            return driver;
        }
        return null;
    }

    public String clearToken(int id) {
        Driver driver = driverRepository.findById(id).get();
        if (driver != null) {
            driver.setToken(null);
            driverRepository.save(driver);
            return "Token cleared and value set to null";
        }
        return "Unable to clear token";
    }

    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }


}
