package com.dinerate.elastic.dinerate_backened.domains.entities;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data @NoArgsConstructor @AllArgsConstructor

@Builder

public class Address {

    @Field(type= FieldType.Keyword)
    private String houseNumber;

    @Field(type= FieldType.Text)
    private String street;

    @Field(type= FieldType.Keyword)
    private String city;

    @Field(type= FieldType.Keyword)
    private String state;

    @Field(type= FieldType.Keyword)
    private String zipCode;

    @Field(type= FieldType.Keyword)
    private String country;
}
