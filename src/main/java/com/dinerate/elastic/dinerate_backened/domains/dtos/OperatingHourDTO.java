package com.dinerate.elastic.dinerate_backened.domains.dtos;

import com.dinerate.elastic.dinerate_backened.domains.entities.TimeRanges;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperatingHourDTO {


    @Valid
    private TimeRangesDTO Sunday;

    @Valid
    private TimeRangesDTO Monday;

    @Valid
    private TimeRangesDTO Tuesday;

    @Valid
    private TimeRangesDTO Wednesday;

    @Valid
    private TimeRangesDTO Thursday;

    @Valid
    private TimeRangesDTO Friday;

    @Valid
    private TimeRangesDTO Saturday;
}
