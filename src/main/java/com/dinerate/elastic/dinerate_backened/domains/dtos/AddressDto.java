package com.dinerate.elastic.dinerate_backened.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {


    @NotBlank(message="House number is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "House number must be alphanumeric")
    private String houseNumber;

    @NotBlank(message="Street is required")
    private String street;

    @NotBlank(message="City is required")
    private String city;

    @NotBlank(message="State is required")
    private String state;

    @NotBlank(message="Zip code is required")
    @Pattern(
            regexp = "^[1-9][0-9]{5}$",
            message = "PIN code must be a valid 6-digit Indian postal code"
    )
    private String zipCode;

    @NotBlank(message="Country is required")
    private String country;
}
