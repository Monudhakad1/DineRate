package com.dinerate.elastic.dinerate_backened.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantSummaryDto {
    private String id;
    private String name;
    private String cusineType;
    private Float averageRating;
    private Integer reviewCount;
    private AddressDto address;
    private List<PhotoDto> photos;
}
