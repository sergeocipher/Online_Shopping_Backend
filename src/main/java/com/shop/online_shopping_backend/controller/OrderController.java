package com.shop.online_shopping_backend.controller;

import com.shop.online_shopping_backend.dto.ApiResponse;
import com.shop.online_shopping_backend.exception.ResourceNotFoundException;
import com.shop.online_shopping_backend.model.Order;
import com.shop.online_shopping_backend.model.User;
import com.shop.online_shopping_backend.repository.UserRepository;
import com.shop.online_shopping_backend.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    private User getUserFromSecurityContext() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userEmail = authentication.getName();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Order>> placeOrder(@RequestBody Map<String, Double> request) {
        Double totalAmount = request.get("totalAmount");
        if (totalAmount == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "totalAmount is required"));
        }

        User user = getUserFromSecurityContext();
        Order order = orderService.placeOrder(user, totalAmount);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Order placed successfully", order));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<Order>>> getMyOrders() {
        User user = getUserFromSecurityContext();
        List<Order> orders = orderService.getOrdersForUser(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "Orders retrieved successfully", orders));
    }
}
