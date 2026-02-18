package com.dinerate.elastic.dinerate_backened.domains.dtos;

import com.dinerate.elastic.dinerate_backened.domains.entities.Address;
import com.dinerate.elastic.dinerate_backened.domains.entities.OperatingHours;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantCreateUpdateRequestDTO {

    @NotBlank(message="Restaurant name is required")
    private String name;

    @NotBlank(message="Cuisine type is required")
    private String cuisineType;

    @NotBlank(message="Contact info is required")
    private String contactInfo;

    @Valid
    private AddressDto address;

    @Valid
    private OperatingHourDTO operatingHours;

    @Size(min=1, message="At least one photo ID is required")
    private List<String> photoIds;

}
