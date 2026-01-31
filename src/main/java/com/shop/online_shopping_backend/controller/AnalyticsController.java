package com.shop.online_shopping_backend.controller;

import com.shop.online_shopping_backend.dto.ApiResponse;
import com.shop.online_shopping_backend.model.Order;
import com.shop.online_shopping_backend.repository.OrderRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
@Tag(name = "Analytics")
@SecurityRequirement(name = "bearerAuth")
public class AnalyticsController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/total-orders")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTotalOrders() {
        long totalOrders = orderRepository.count();
        Map<String, Object> data = new HashMap<>();
        data.put("totalOrders", totalOrders);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Total orders retrieved successfully", data)
        );
    }

    @GetMapping("/total-revenue")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTotalRevenue() {
        List<Order> orders = orderRepository.findAll();
        Double totalRevenue = orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();
        Map<String, Object> data = new HashMap<>();
        data.put("totalRevenue", totalRevenue);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Total revenue retrieved successfully", data)
        );
    }
}
