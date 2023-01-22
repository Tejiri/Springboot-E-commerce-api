package com.example.store.utils;

import com.example.store.entity.Customer;
import com.example.store.entity.Driver;
import com.example.store.entity.Supervisor;
import com.example.store.use_case.customer.CustomerService;
import com.example.store.use_case.driver.DriverService;
import com.example.store.use_case.supervisor.SupervisorService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class AuthorisationFilter implements Filter {

    CustomerService customerService;
    DriverService driverService;
    SupervisorService supervisorService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI().toLowerCase();

        if (requestURI.contains("/check-credentials") ||
                requestURI.contains("customer/create")
        ) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (customerIsVerified(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (driverIsVerified(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (supervisorIsVerified(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    private boolean customerIsVerified(HttpServletRequest request) {
        String requestURI = request.getRequestURI().toLowerCase();
        String token = request.getHeader("AUTHORIZATION").toString();

        Customer customer = customerService.checkCredentials(token);

        if (customer != null) {
            if (requestURI.startsWith("/api/v1/order/create/") ||
                    requestURI.startsWith("/api/v1/order/delete/") ||
                    requestURI.startsWith("/api/v1/customer/update/address/")) {
                String[] parts = requestURI.substring(1).split("/");
                int id = Integer.parseInt(parts[5]);
                return customer.getId() == id;
            } else if (requestURI.startsWith("/api/v1/customer/delete/") ||
                    requestURI.startsWith("/api/v1/customer/logout/")) {
                String[] parts = requestURI.substring(1).split("/");
                int id = Integer.parseInt(parts[4]);
                return customer.getId() == id;
            } else if (requestURI.startsWith("/api/v1/product-category/get/")
            ) {
                return true;
            }
        }
        return false;
    }

    private boolean driverIsVerified(HttpServletRequest request) {
        String requestURI = request.getRequestURI().toLowerCase();
        String token = request.getHeader("AUTHORIZATION").toString();

        Driver driver = driverService.checkCredentials(token);

        if (driver != null) {
            if (requestURI.startsWith("/api/v1/driver/get/orders/")) {
                String[] parts = requestURI.substring(1).split("/");
                int id = Integer.parseInt(parts[5]);
                return driver.getId() == id;
            } else if (requestURI.startsWith("/api/v1/driver/logout/")) {
                String[] parts = requestURI.substring(1).split("/");
                int id = Integer.parseInt(parts[4]);
                return driver.getId() == id;
            } else if (requestURI.startsWith("/api/v1/order/update/status/")) {
                String[] parts = requestURI.substring(1).split("/");
                int id = Integer.parseInt(parts[7]);
                return driver.getId() == id;
            }
        }
        return false;
    }

    private boolean supervisorIsVerified(HttpServletRequest request) {
        String requestURI = request.getRequestURI().toLowerCase();
        String token = request.getHeader("AUTHORIZATION").toString();

        Supervisor supervisor = supervisorService.checkCredentials(token);

        if (supervisor != null) {
            if (requestURI.startsWith("/api/v1/product/create/") ||
                    requestURI.startsWith("/api/v1/order/update/driver/") ||
                    requestURI.startsWith("/api/v1/product/delete/") ||
                    requestURI.startsWith("/api/v1/order-status/get/")) {
                return true;
            } else if (requestURI.startsWith("/api/v1/supervisor/logout/")) {
                String[] parts = requestURI.substring(1).split("/");
                int id = Integer.parseInt(parts[4]);
                return supervisor.getId() == id;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
