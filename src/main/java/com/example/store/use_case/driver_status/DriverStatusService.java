package com.example.store.use_case.driver_status;

import com.example.store.entity.DriverStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverStatusService {
    private final DriverStatusRepository driverStatusRepository;
    public DriverStatus getDriverStatusById(int id){
        DriverStatus driverStatus = driverStatusRepository.findById(2).get();
        return driverStatus;
    }

}
