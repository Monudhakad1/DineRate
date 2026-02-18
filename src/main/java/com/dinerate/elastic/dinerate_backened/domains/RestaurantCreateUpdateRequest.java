package com.dinerate.elastic.dinerate_backened.domains;

import com.dinerate.elastic.dinerate_backened.domains.entities.Address;
import com.dinerate.elastic.dinerate_backened.domains.entities.OperatingHours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.core.index.AliasAction;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantCreateUpdateRequest {

    private String name;

    private String cuisineType;

    private String contactInfo;

    private Address address;

    private OperatingHours operatingHours;

    private List<String> photoIds;

}
