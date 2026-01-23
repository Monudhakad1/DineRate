package domains.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor  // don't use @data in entity due to cyclic issue in elasticsearch
public class operatingHours {

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
