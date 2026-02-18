package com.dinerate.elastic.dinerate_backened.domains.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TimeRangesDTO {

    @NotBlank(message = "Open time cannot be blank")
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Open time must be in HH:mm format")
    private String openTime;

    @NotBlank(message = "Open time cannot be blank")
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Open time must be in HH:mm format")
    private String closeTime;

}
