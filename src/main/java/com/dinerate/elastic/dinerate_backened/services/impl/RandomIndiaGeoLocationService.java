package com.dinerate.elastic.dinerate_backened.services.impl;

import com.dinerate.elastic.dinerate_backened.domains.entities.Address;
import com.dinerate.elastic.dinerate_backened.domains.entities.GeoLocation;
import com.dinerate.elastic.dinerate_backened.services.GeoLocationServices;
import org.springframework.stereotype.Service;


import java.util.Random;

@Service
public class RandomIndiaGeoLocationService implements GeoLocationServices {

    private static final float MIN_LATITUDE = 51.28f;
    private static final float MAX_LATITUDE = 51.685f;
    private static final float MIN_LONGITUDE = -0.489f;
    private static final float MAX_LONGITUDE = 0.236f;
    @Override
    public GeoLocation geoLocate(Address address) {

        Random random = new Random();
        double latitude = MIN_LATITUDE + (MAX_LATITUDE - MIN_LATITUDE) * random.nextDouble();
        double longitude = MIN_LONGITUDE + (MAX_LONGITUDE - MIN_LONGITUDE) * random.nextDouble();

        return GeoLocation.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}
