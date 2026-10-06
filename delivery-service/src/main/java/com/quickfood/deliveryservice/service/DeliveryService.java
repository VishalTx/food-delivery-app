package com.quickfood.deliveryservice.service;

import com.quickfood.deliveryservice.model.Delivery;
import com.quickfood.deliveryservice.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public Delivery create(Delivery delivery) { return deliveryRepository.save(delivery); }
    public Optional<Delivery> findById(Long id) { return deliveryRepository.findById(id); }
}