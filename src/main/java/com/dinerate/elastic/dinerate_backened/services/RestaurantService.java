package com.dinerate.elastic.dinerate_backened.services;

import com.dinerate.elastic.dinerate_backened.domains.RestaurantCreateUpdateRequest;
import com.dinerate.elastic.dinerate_backened.domains.entities.Restaurants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RestaurantService {
    Restaurants createRestaurant(RestaurantCreateUpdateRequest restaurant);

    Page<Restaurants> searchRestaurants(
            String query,
            Float minRating,
            Float latitude,
            Float longitude,
            Float radius,
            Pageable pageable

    );
    Optional<Restaurants> getRestaurant(String id);

    Optional<Restaurants> updateRestaurant(String id, RestaurantCreateUpdateRequest restaurantCreateUpdateRequest);

    void deleteRestaurant(String id);
}
