package com.dinerate.elastic.dinerate_backened.services;

import com.dinerate.elastic.dinerate_backened.domains.ReviewCreateUpdateRequest;
import com.dinerate.elastic.dinerate_backened.domains.entities.Review;
import com.dinerate.elastic.dinerate_backened.domains.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewServices {

    Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest review);

    Page<Review> listReviews(String restaurantId,Pageable pageable);

    Optional<Review> getReview(String restaurantId, String reviewId);

    Review updateReview(User author, String restaurantId, String reviewId, ReviewCreateUpdateRequest review);

        void deleteReview(User author, String restaurantId, String reviewId);
}
