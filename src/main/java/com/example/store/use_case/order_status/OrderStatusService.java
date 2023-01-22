package com.example.store.use_case.order_status;

import com.example.store.entity.Order;
import com.example.store.entity.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatus findOrderStatusByStatusName(String status) {
        OrderStatus orderStatus = orderStatusRepository.findByStatus(status);
        if (orderStatus != null) {
            return orderStatus;
        }
        return null;
    }

    public List<Order> findListOfOrdersByStatusName(String statusName) {
        OrderStatus orderStatus = findOrderStatusByStatusName(statusName);
        return orderStatus.getOrders();
    }
}
