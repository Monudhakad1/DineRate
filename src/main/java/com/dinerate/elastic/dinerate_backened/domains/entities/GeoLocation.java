package com.dinerate.elastic.dinerate_backened.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor

public class GeoLocation {
    private Double latitude;
    private Double longitude;
}
