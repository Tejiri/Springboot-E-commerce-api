package com.example.store.use_case.order;

import com.example.store.use_case.customer.CustomerService;
import com.example.store.use_case.driver.DriverService;
import com.example.store.dto.NewOrderDTO;
import com.example.store.entity.*;
import com.example.store.factory.ENTITY_Factory;
import com.example.store.use_case.driver_status.DriverStatusService;
import com.example.store.use_case.order_status.OrderStatusService;
import com.example.store.use_case.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderStatusService orderStatusService;
    private final DriverService driverService;
    private final ENTITY_Factory entityFactory;
    private final DriverStatusService driverStatusService;

    public Order createOrder(NewOrderDTO newOrderDTO, int productId, int customerId) {
        Order order = entityFactory.create(newOrderDTO);
        Product product = productService.findProductById(productId);

        if (order.getQuantity() > product.getStockQuantity()) {
            return null;
        } else {
            if (product != null) {
                product.setStockQuantity(product.getStockQuantity() - order.getQuantity());
                Customer customer = customerService.findCustomerById(customerId);
                if (customer != null) {
                    order.setProduct(product);
                    order.setCustomer(customer);
                    order.setOrderStatus(orderStatusService.findOrderStatusByStatusName("pending"));
                    return orderRepository.save(order);
                }
                return null;
            }
        }
        return null;
    }

    public Order updateOrderDriver(int orderId, int driverId) {
        Order order = orderRepository.findById(orderId).get();
        if (order != null && order.getDriver() == null && order.getOrderStatus().getStatus().equals("pending")) {
            Driver driver = driverService.findDriverById(driverId);
            if (driver != null) {
                if (driver.getDriverStatus().getStatus().equals("available")) {
                    DriverStatus driverStatus = driverStatusService.getDriverStatusById(2);
                    driver.setDriverStatus(driverStatus);
                    driverService.saveDriver(driver);

                    OrderStatus orderStatus = orderStatusService.findOrderStatusByStatusName("processing");
                    order.setDriver(driver);
                    order.setOrderStatus(orderStatus);
                    return orderRepository.save(order);
                }


            }
        }
        return null;
    }

    public Order updateOrderStatus(int orderId, String statusName) {
        Order order = orderRepository.findById(orderId).get();
        OrderStatus orderStatus = orderStatusService.findOrderStatusByStatusName(statusName);
        if (orderStatus != null && order != null) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        return null;
    }

    public String deleteOrder(int orderId, int customerId) {
        Order order = orderRepository.findById(orderId).get();
        if (order != null) {
            if (order.getCustomer().getId() == customerId) {
                orderRepository.delete(order);
                return "Order has been deleted successfully";
            }
        }
        return null;
    }
}
