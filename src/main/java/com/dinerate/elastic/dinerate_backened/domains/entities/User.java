package com.dinerate.elastic.dinerate_backened.domains.entities;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "users")

public class User {

    @Field(type= FieldType.Keyword)
    private String id;

    @Field(type= FieldType.Text)
    private String username;

    @Field(type= FieldType.Text)
    private String firstName;

    @Field(type= FieldType.Text)
    private String lastName;
}
