package com.dinerate.elastic.dinerate_backened.Mappers;

import com.dinerate.elastic.dinerate_backened.domains.RestaurantCreateUpdateRequest;
import com.dinerate.elastic.dinerate_backened.domains.dtos.GeoPointDto;
import com.dinerate.elastic.dinerate_backened.domains.dtos.RestaurantCreateUpdateRequestDTO;
import com.dinerate.elastic.dinerate_backened.domains.dtos.RestaurantSummaryDto;
import com.dinerate.elastic.dinerate_backened.domains.dtos.RestaurantsDTO;
import com.dinerate.elastic.dinerate_backened.domains.entities.Restaurants;
import com.dinerate.elastic.dinerate_backened.domains.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface RestaurantsMapper {
    RestaurantCreateUpdateRequest toRestaurantCreateUpdateRequest(RestaurantCreateUpdateRequestDTO restaurantCreateUpdateRequestDTO);

    RestaurantsDTO toRestaurantsDTO(Restaurants restaurants);

    RestaurantSummaryDto toRestaurantSummaryDto(Restaurants restaurants);

    @Named("populateTotalReviews")
    private Integer populateTotalReviews(List<Review> reviews){
        return reviews.size();
    }

    @Mapping(target = "latitude", source = "lat")
    @Mapping(target = "longitude", source = "lon")
    GeoPointDto toGeoPointDto(GeoPoint geoPoint);

}
