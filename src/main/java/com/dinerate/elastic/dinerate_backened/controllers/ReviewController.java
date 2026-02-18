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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    @GetMapping
    public ResponseEntity<Page<ReviewDto>> listReviews(
            @PathVariable String restaurantId,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "postedDate",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ){

        Page<ReviewDto> reviewPage = reviewServices
                .listReviews(restaurantId, pageable)
                .map(reviewMapper::toReviewDto);

        return ResponseEntity.ok(reviewPage);
    }
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(
            @PathVariable String restaurantId,
            @PathVariable String reviewId
    ) {
        return reviewServices.getReview(restaurantId, reviewId)
                .map(reviewMapper::toReviewDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }


    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable String restaurantId,
            @PathVariable String reviewId,
            @Valid @RequestBody ReviewCreateUpdateRequestDto review,
            @AuthenticationPrincipal Jwt jwt
    ) {
        ReviewCreateUpdateRequest reviewCreateUpdateRequest = reviewMapper.toReviewCreateUpdateRequest(review);
        User user=jwtToUser(jwt);
        Review updatedReview = reviewServices.updateReview(user, restaurantId, reviewId, reviewCreateUpdateRequest);
        return ResponseEntity.ok(reviewMapper.toReviewDto(updatedReview));
    }



    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable String restaurantId,
            @PathVariable String reviewId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        User user=jwtToUser(jwt);
        reviewServices.deleteReview(user, restaurantId, reviewId);
        return ResponseEntity.noContent().build();
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
