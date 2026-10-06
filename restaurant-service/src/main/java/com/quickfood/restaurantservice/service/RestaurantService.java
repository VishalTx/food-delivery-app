package com.quickfood.restaurantservice.service;

import com.quickfood.restaurantservice.model.Restaurant;
import com.quickfood.restaurantservice.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant create(Restaurant restaurant) { return restaurantRepository.save(restaurant); }
    public Optional<Restaurant> findById(Long id) { return restaurantRepository.findById(id); }
}