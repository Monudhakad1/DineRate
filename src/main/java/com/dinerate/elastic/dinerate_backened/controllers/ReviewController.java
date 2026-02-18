package com.dinerate.elastic.dinerate_backened.controllers;

import com.dinerate.elastic.dinerate_backened.Mappers.ReviewMapper;
import com.dinerate.elastic.dinerate_backened.domains.ReviewCreateUpdateRequest;
import com.dinerate.elastic.dinerate_backened.domains.dtos.ReviewCreateUpdateRequestDto;
import com.dinerate.elastic.dinerate_backened.domains.dtos.ReviewDto;
import com.dinerate.elastic.dinerate_backened.domains.entities.Review;
import com.dinerate.elastic.dinerate_backened.domains.entities.User;
import com.dinerate.elastic.dinerate_backened.services.ReviewServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewMapper reviewMapper;
    private final ReviewServices reviewServices;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable String restaurantId,
            @Valid @RequestBody ReviewCreateUpdateRequestDto review,
            @AuthenticationPrincipal Jwt jwt){
        ReviewCreateUpdateRequest reviewCreateUpdateRequest = reviewMapper.toReviewCreateUpdateRequest(review);

        User user=jwtToUser(jwt);

        Review createdReview= reviewServices.createReview(user,restaurantId,reviewCreateUpdateRequest);

        return ResponseEntity.ok(reviewMapper.toReviewDto(createdReview));
    }
    private User jwtToUser(Jwt jwt){
        return User.builder()
                .id(jwt.getId())
                .username(jwt.getClaimAsString("preferred_username"))
                .firstName(jwt.getClaimAsString("first_Name"))
                .lastName(jwt.getClaimAsString("last_Name"))
                .build();
    }
}
