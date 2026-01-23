package domains.entities;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class TimeRanges {

    @Field(type= FieldType.Keyword)
    private String openTime;

    @Field(type= FieldType.Keyword)
    private String closeTime;

}
