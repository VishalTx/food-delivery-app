package com.quickfood.deliveryservice.controller;

import com.quickfood.deliveryservice.model.Delivery;
import com.quickfood.deliveryservice.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) { this.deliveryService = deliveryService; }

    @PostMapping
    public Delivery create(@RequestBody Delivery delivery) { return deliveryService.create(delivery); }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> findById(@PathVariable Long id) {
        return deliveryService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}