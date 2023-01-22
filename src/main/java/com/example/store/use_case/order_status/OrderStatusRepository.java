package com.example.store.use_case.order_status;

import com.example.store.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus,Integer> {
    OrderStatus findByStatus(String status);
}
