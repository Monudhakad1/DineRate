package com.dinerate.elastic.dinerate_backened.domains.dtos;

import com.dinerate.elastic.dinerate_backened.domains.entities.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class RestaurantsDTO {
    private String id;

    private String name;

    private String cuisineType;

    private String contactInfo;

    private AddressDto address;

    private Float averageRating;

    private GeoPointDto geoLocation;

    private List<ReviewDto> reviews = new ArrayList<>();

    private OperatingHourDTO operatingHours;

    private List<PhotoDto> photos;

    private UserDto createdBy;

    private Integer totalReviews;

}
