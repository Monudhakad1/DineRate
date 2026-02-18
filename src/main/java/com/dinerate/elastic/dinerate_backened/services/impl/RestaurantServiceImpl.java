package com.dinerate.elastic.dinerate_backened.services.impl;

import com.dinerate.elastic.dinerate_backened.domains.GeoLocation;
import com.dinerate.elastic.dinerate_backened.domains.RestaurantCreateUpdateRequest;
import com.dinerate.elastic.dinerate_backened.domains.entities.Address;
import com.dinerate.elastic.dinerate_backened.domains.entities.Photo;
import com.dinerate.elastic.dinerate_backened.domains.entities.Restaurants;
import com.dinerate.elastic.dinerate_backened.repositories.RestaurantRepository;
import com.dinerate.elastic.dinerate_backened.services.GeoLocationServices;
import com.dinerate.elastic.dinerate_backened.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final GeoLocationServices geoLocationServices;

    @Override
    public Restaurants createRestaurant(RestaurantCreateUpdateRequest request) {
        Address address = request.getAddress();
        GeoLocation geoLocation = geoLocationServices.geoLocate(address);
        GeoPoint geoPoint=new GeoPoint(geoLocation.getLatitude() , geoLocation.getLongitude());
        List<String> photoIds = request.getPhotoIds();
        List<Photo> photos = photoIds.stream()
                .map(photoUrl -> Photo.builder()
                        .url(photoUrl)
                        .uploadedTime(LocalDateTime.now())
                        .build())
                .toList();

        Restaurants restaurants = Restaurants.builder()
                .name(request.getName())
                .cuisineType(request.getCuisineType())
                .contactInfo(request.getContactInfo())
                .address(address)
                .geoLocation(geoPoint)
                .photos(photos)
                .operatingHours(request.getOperatingHours())
                .averageRating(0f)
                .photos(photos)
                .build();

        return restaurantRepository.save(restaurants);
    }

    @Override
    public Page<Restaurants> searchRestaurants(String query, Float minRating, Float latitude, Float longitude, Float radius, Pageable pageable) {
        if(minRating!=null && (query==null || query.isEmpty())){return restaurantRepository.findByAverageRatingGreaterThanEqual(minRating , pageable);}
        Float searchMinRating = null==minRating ? 0f : minRating;
        if(null !=query && !query.isEmpty()){
            return restaurantRepository.findByQueryAndMinRating(query, searchMinRating, pageable);
        }
        if(null!=latitude && null!=longitude && null!=radius){
            return restaurantRepository.findByGeoLocationNear(latitude, longitude , radius, pageable);
        }
        return restaurantRepository.findAll(pageable);
    }

    @Override
    public Optional<Restaurants> getRestaurant(String id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public Optional<Restaurants> updateRestaurant(String id, RestaurantCreateUpdateRequest request) {
        Restaurants restaurants = getRestaurant(id)
                .orElseThrow(()-> new RuntimeException("Restaurant not found with id: " + id));
        GeoLocation newGeoLocation=geoLocationServices.geoLocate(
                request.getAddress());
        GeoPoint newGeoPoint=new GeoPoint(newGeoLocation.getLatitude() , newGeoLocation.getLongitude());
        List<String> photoIds = request.getPhotoIds();
        List<Photo> photos = photoIds.stream()
                .map(photoUrl -> Photo.builder()
                        .url(photoUrl)
                        .uploadedTime(LocalDateTime.now())
                        .build())
                .toList();
        restaurants.setName(request.getName());
        restaurants.setCuisineType(request.getCuisineType());
        restaurants.setContactInfo(request.getContactInfo());
        restaurants.setAddress(request.getAddress());
        restaurants.setGeoLocation(newGeoPoint);
        restaurants.setOperatingHours(request.getOperatingHours());
        restaurants.setPhotos(photos);
        return Optional.of(restaurantRepository.save(restaurants));
    }

    @Override
    public void deleteRestaurant(String id) {
        restaurantRepository.deleteById(id);
    }


}
