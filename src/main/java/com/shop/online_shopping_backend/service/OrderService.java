package com.shop.online_shopping_backend.service;

import com.shop.online_shopping_backend.model.Order;
import com.shop.online_shopping_backend.model.User;
import com.shop.online_shopping_backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order placeOrder(User user, Double totalAmount) {
        if (user == null || totalAmount == null) {
            throw new IllegalArgumentException("User and totalAmount cannot be null");
        }
        if (totalAmount <= 0) {
            throw new IllegalArgumentException("Total amount must be greater than 0");
        }

        Order order = new Order(user, totalAmount);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersForUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return orderRepository.findByUserId(user.getId());
    }
}
