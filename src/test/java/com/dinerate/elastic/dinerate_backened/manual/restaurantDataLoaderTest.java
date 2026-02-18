package com.dinerate.elastic.dinerate_backened.manual;

import com.dinerate.elastic.dinerate_backened.domains.RestaurantCreateUpdateRequest;
import com.dinerate.elastic.dinerate_backened.domains.entities.Address;
import com.dinerate.elastic.dinerate_backened.domains.entities.OperatingHours;
import com.dinerate.elastic.dinerate_backened.domains.entities.Photo;
import com.dinerate.elastic.dinerate_backened.domains.entities.TimeRanges;
import com.dinerate.elastic.dinerate_backened.services.PhotoService;
import com.dinerate.elastic.dinerate_backened.services.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class restaurantDataLoaderTest {
    @Autowired
    private RestaurantService restaurantService;


    @Autowired
    private PhotoService photoService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    @Rollback(false) // Allow changes to persist
    public void createSampleRestaurants() {
        List<RestaurantCreateUpdateRequest> restaurants = createRestaurantData();
        restaurants.forEach(restaurant -> {
            String fileName = restaurant.getPhotoIds().getFirst();
            Resource resource = resourceLoader.getResource("classpath:testdata/" + fileName);
            MultipartFile multipartFile;
            try {
                multipartFile = new MockMultipartFile(
                        "file", // parameter name
                        fileName, // original filename
                        MediaType.IMAGE_PNG_VALUE,
                        resource.getInputStream()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            // Call the service method
            Photo uploadedPhoto = photoService.uploadPhoto(multipartFile);

            restaurant.setPhotoIds(List.of(uploadedPhoto.getUrl()));

            restaurantService.createRestaurant(restaurant);

            System.out.println("Created restaurant: " + restaurant.getName());
        });
    }

    private List<RestaurantCreateUpdateRequest> createRestaurantData() {
        return Arrays.asList(
                createRestaurant(
                        "The Golden Dragon",
                        "Chinese",
                        "+91 9876543210",
                        createAddress("12A", "MG Road", null, "Mumbai", "Maharashtra", "400001", "India"),
                        createStandardOperatingHours("11:30", "23:00", "11:30", "23:30"),
                        "anh-nguyen-kcA-c3f_3FE-unsplash.jpg"
                ),
                createRestaurant(
                        "La Petite Maison",
                        "French",
                        "+91 9876543211",
                        createAddress("54", "Park Street", null, "Kolkata", "West Bengal", "700016", "India"),
                        createStandardOperatingHours("12:00", "22:30", "12:00", "23:00"),
                        "image1.jpg"
                ),
                createRestaurant(
                        "Raj Pavilion",
                        "Indian",
                        "+91 9876543212",
                        createAddress("27B", "Brigade Road", null, "Bengaluru", "Karnataka", "560001", "India"),
                        createStandardOperatingHours("12:00", "23:00", "12:00", "23:30"),
                        "image2.jpg"
                ),
                createRestaurant(
                        "Sushi Master",
                        "Japanese",
                        "+91 9876543213",
                        createAddress("8", "Anna Salai", null, "Chennai", "Tamil Nadu", "600002", "India"),
                        createStandardOperatingHours("11:30", "22:00", "11:30", "22:30"),
                        "image4.jpg"
                ),
                createRestaurant(
                        "The Rustic Olive",
                        "Italian",
                        "+91 9876543214",
                        createAddress("92", "Connaught Place", null, "Delhi", "Delhi", "110001", "India"),
                        createStandardOperatingHours("11:00", "23:00", "11:00", "23:30"),
                        "casey-lee-awj7sRviVXo-unsplash.jpg"
                ),
                createRestaurant(
                        "El Toro",
                        "Spanish",
                        "+91 9876543215",
                        createAddress("15", "Banjara Hills", null, "Hyderabad", "Telangana", "500034", "India"),
                        createStandardOperatingHours("12:00", "23:00", "12:00", "23:30"),
                        "casey-lee-awj7sRviVXo-unsplash.jpg"
                ),
                createRestaurant(
                        "The Greek House",
                        "Greek",
                        "+91 9876543216",
                        createAddress("32", "FC Road", null, "Pune", "Maharashtra", "411004", "India"),
                        createStandardOperatingHours("12:00", "22:30", "12:00", "23:00"),
                        "image2.jpg"
                ),
                createRestaurant(
                        "Seoul Kitchen",
                        "Korean",
                        "+91 9876543217",
                        createAddress("71", "Sector 18", null, "Noida", "Uttar Pradesh", "201301", "India"),
                        createStandardOperatingHours("11:30", "22:00", "11:30", "22:30"),
                        "joseph-gonzalez-fdlZBWIP0aM-unsplash.jpg"
                ),
                createRestaurant(
                        "Thai Orchid",
                        "Thai",
                        "+91 9876543218",
                        createAddress("45", "Law Garden", null, "Ahmedabad", "Gujarat", "380006", "India"),
                        createStandardOperatingHours("11:00", "22:30", "11:00", "23:00"),
                        "khloe-arledge-ND3edEmzcdQ-unsplash.jpg"
                ),
                createRestaurant(
                        "The Burger Joint",
                        "American",
                        "+91 9876543219",
                        createAddress("88", "Marine Drive", null, "Mumbai", "Maharashtra", "400020", "India"),
                        createStandardOperatingHours("11:00", "23:00", "11:00", "23:30"),
                        "michele-blackwell-rAyCBQTH7ws-unsplash.jpg"
                )
        );
    }


    private RestaurantCreateUpdateRequest createRestaurant(
            String name,
            String cuisineType,
            String contactInformation,
            Address address,
            OperatingHours operatingHours,
            String photoId
    )
    {
        return RestaurantCreateUpdateRequest.builder()
                .name(name)
                .cuisineType(cuisineType)
                .contactInfo(contactInformation)
                .address(address)
                .operatingHours(operatingHours)
                .photoIds(List.of(photoId))
                .build();
    }

    private Address createAddress(
            String streetNumber,
            String streetName,
            String unit,
            String city,
            String state,
            String postalCode,
            String country
    ) {
        Address address = new Address();
        address.setHouseNumber(streetNumber);
        address.setStreet(streetName);
        // Note: Address entity doesn't have 'unit' field, only houseNumber
        address.setCity(city);
        address.setState(state);
        address.setZipCode(postalCode);
        address.setCountry(country);
        return address;
    }

    private OperatingHours createStandardOperatingHours(
            String weekdayOpen,
            String weekdayClose,
            String weekendOpen,
            String weekendClose
    ) {
        TimeRanges weekday = new TimeRanges();
        weekday.setOpenTime(weekdayOpen);
        weekday.setCloseTime(weekdayClose);

        TimeRanges weekend = new TimeRanges();
        weekend.setOpenTime(weekendOpen);
        weekend.setCloseTime(weekendClose);

        OperatingHours hours = new OperatingHours();
        hours.setMonday(weekday);
        hours.setTuesday(weekday);
        hours.setWednesday(weekday);
        hours.setThursday(weekday);
        hours.setFriday(weekend);
        hours.setSaturday(weekend);
        hours.setSunday(weekend);

        return hours;
    }
}
