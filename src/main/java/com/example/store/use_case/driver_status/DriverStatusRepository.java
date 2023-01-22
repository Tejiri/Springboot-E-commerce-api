package com.example.store.use_case.driver_status;

import com.example.store.entity.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverStatusRepository extends JpaRepository<DriverStatus, Integer> {
}
