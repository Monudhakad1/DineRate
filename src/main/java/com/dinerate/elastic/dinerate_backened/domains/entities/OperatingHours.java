package com.dinerate.elastic.dinerate_backened.domains.entities;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor @NoArgsConstructor  // don't use @data in entity due to cyclic issue in elasticsearch
public class OperatingHours {

    @Field(type= FieldType.Nested)
    private TimeRanges Sunday;

    @Field(type= FieldType.Nested)
    private TimeRanges Monday;

    @Field(type= FieldType.Nested)
    private TimeRanges Tuesday;

    @Field(type= FieldType.Nested)
    private TimeRanges Wednesday;

    @Field(type= FieldType.Nested)
    private TimeRanges Thursday;

    @Field(type= FieldType.Nested)
    private TimeRanges Friday;

    @Field(type= FieldType.Nested)
    private TimeRanges Saturday;
}
