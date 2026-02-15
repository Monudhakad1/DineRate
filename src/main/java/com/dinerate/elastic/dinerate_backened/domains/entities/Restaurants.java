package com.dinerate.elastic.dinerate_backened.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.ArrayList;
import java.util.List;

@Document(indexName = "restaurants")  // it creates a single json
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurants {

    @Id
    private String id;

    @Field(type= FieldType.Text)
    private String name;

    @Field(type= FieldType.Text)
    private String cuisineType;

    @Field(type= FieldType.Keyword)
    private String contactInfo;

    @Field(type= FieldType.Object)
    private Address address;

    @Field(type= FieldType.Float)
    private Float averageRating;

    @GeoPointField
    private GeoPoint geoLocation;

    @Field(type= FieldType.Nested)
    private List<Review> reviews = new ArrayList<>();


    @Field(type= FieldType.Object)
    private TimeRanges operatingHours;

    @Field(type= FieldType.Nested)
    private List<Photo>photos;

    @Field(type= FieldType.Object)
    private User createdBy;
}
