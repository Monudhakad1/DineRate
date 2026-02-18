package com.dinerate.elastic.dinerate_backened.controllers;

import com.dinerate.elastic.dinerate_backened.Mappers.RestaurantsMapper;
import com.dinerate.elastic.dinerate_backened.domains.RestaurantCreateUpdateRequest;
import com.dinerate.elastic.dinerate_backened.domains.dtos.RestaurantCreateUpdateRequestDTO;
import com.dinerate.elastic.dinerate_backened.domains.dtos.RestaurantSummaryDto;
import com.dinerate.elastic.dinerate_backened.domains.dtos.RestaurantsDTO;
import com.dinerate.elastic.dinerate_backened.domains.entities.Restaurants;
import com.dinerate.elastic.dinerate_backened.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantsMapper restaurantsMapper;

    @PostMapping
    public ResponseEntity<RestaurantsDTO> createRestaurant(@Valid @RequestBody RestaurantCreateUpdateRequestDTO request) {
        RestaurantCreateUpdateRequest restaurantCreateUpdateRequest = restaurantsMapper.toRestaurantCreateUpdateRequest(request);
        Restaurants restaurants = restaurantService.createRestaurant(restaurantCreateUpdateRequest);
        RestaurantsDTO restaurantsDTO = restaurantsMapper.toRestaurantsDTO(restaurants);
        return ResponseEntity.ok(restaurantsDTO);

    }

    @GetMapping
    public Page<RestaurantSummaryDto> searchRestuarants(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Float minRating,
            @RequestParam(required = false) Float latitude,
            @RequestParam(required = false) Float longitude,
            @RequestParam(required = false) Float radius,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Page<Restaurants> searchRestaurant = restaurantService.searchRestaurants(
                q, minRating, latitude, longitude, radius, PageRequest.of(page - 1, size)
        );

        return searchRestaurant.map(restaurantsMapper::toRestaurantSummaryDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantsDTO> getRestaurantById(@PathVariable String id) {
        return restaurantService.getRestaurant(id)
                .map(restaurantsMapper::toRestaurantsDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantsDTO> updateRestaurant(
            @PathVariable String id,
            @Valid @RequestBody RestaurantCreateUpdateRequestDTO requestDTO
    ) {
        RestaurantCreateUpdateRequest request = restaurantsMapper.toRestaurantCreateUpdateRequest(requestDTO);
       Optional<Restaurants> updatedRestaurant=restaurantService.updateRestaurant(id, request);
       return ResponseEntity.ok(restaurantsMapper.toRestaurantsDTO(updatedRestaurant.orElse(null)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
