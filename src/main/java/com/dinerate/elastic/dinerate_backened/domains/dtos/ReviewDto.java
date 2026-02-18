package com.dinerate.elastic.dinerate_backened.domains.dtos;

import com.dinerate.elastic.dinerate_backened.domains.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {

    private String id;

    private String content;

    private Integer rating;

    private LocalDateTime postedDate;

    private LocalDateTime lastEdited;

    private List<PhotoDto> photos = new ArrayList<>();


    private UserDto writtenBy;

}
