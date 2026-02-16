package com.dinerate.elastic.dinerate_backened.services;

import com.dinerate.elastic.dinerate_backened.domains.entities.Address;
import com.dinerate.elastic.dinerate_backened.domains.entities.GeoLocation;

public interface GeoLocationServices {

    GeoLocation geoLocate(Address address);
}
