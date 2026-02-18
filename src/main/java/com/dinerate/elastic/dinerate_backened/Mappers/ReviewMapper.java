package com.dinerate.elastic.dinerate_backened.Mappers;

import com.dinerate.elastic.dinerate_backened.domains.ReviewCreateUpdateRequest;
import com.dinerate.elastic.dinerate_backened.domains.dtos.ReviewCreateUpdateRequestDto;
import com.dinerate.elastic.dinerate_backened.domains.dtos.ReviewDto;
import com.dinerate.elastic.dinerate_backened.domains.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    ReviewCreateUpdateRequest toReviewCreateUpdateRequest(ReviewCreateUpdateRequestDto dto);

    ReviewDto toReviewDto(Review review);

}
