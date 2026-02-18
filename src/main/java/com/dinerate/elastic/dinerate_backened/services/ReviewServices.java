package com.dinerate.elastic.dinerate_backened.services;

import com.dinerate.elastic.dinerate_backened.domains.ReviewCreateUpdateRequest;
import com.dinerate.elastic.dinerate_backened.domains.entities.Review;
import com.dinerate.elastic.dinerate_backened.domains.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewServices {

    Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest review);

    Page<Review> listReviews(String restaurantId,Pageable pageable);

}
