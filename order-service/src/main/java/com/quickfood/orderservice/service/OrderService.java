package com.quickfood.orderservice.service;

import com.quickfood.orderservice.model.Order;
import com.quickfood.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order create(Order order) { return orderRepository.save(order); }
    public Optional<Order> findById(Long id) { return orderRepository.findById(id); }
}