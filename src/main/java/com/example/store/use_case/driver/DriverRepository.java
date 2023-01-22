package com.example.store.use_case.driver;

import com.example.store.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Driver findByEmail(String email);
    Driver findByToken(String token);
}
