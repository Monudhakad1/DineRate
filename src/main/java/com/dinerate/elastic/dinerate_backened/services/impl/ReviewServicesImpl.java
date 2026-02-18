package com.dinerate.elastic.dinerate_backened.services.impl;

import com.dinerate.elastic.dinerate_backened.domains.ReviewCreateUpdateRequest;
import com.dinerate.elastic.dinerate_backened.domains.entities.Photo;
import com.dinerate.elastic.dinerate_backened.domains.entities.Restaurants;
import com.dinerate.elastic.dinerate_backened.domains.entities.Review;
import com.dinerate.elastic.dinerate_backened.domains.entities.User;
import com.dinerate.elastic.dinerate_backened.exceptions.ReviewNotAllowedException;
import com.dinerate.elastic.dinerate_backened.repositories.RestaurantRepository;
import com.dinerate.elastic.dinerate_backened.services.ReviewServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ReviewServicesImpl implements ReviewServices {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest review) {
        Restaurants restaurants = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        boolean exists = restaurants.getReviews().stream()
                .anyMatch(r -> r.getWrittenBy().getId().equals(author.getId()));
        if (exists) {
            throw new ReviewNotAllowedException("User has already reviewed this restaurant");
        }
        List<Photo> photos = review.getPhotoUrls().stream().map(url -> Photo.builder().url(url).uploadedTime(LocalDateTime.now()).build()).toList();

        String reviewId = UUID.randomUUID().toString();
        Review newReview = Review.builder()
                .id(reviewId)
                .content(review.getContent())
                .rating(review.getRating())
                .postedDate(LocalDateTime.now())
                .lastEdited(LocalDateTime.now())
                .photos(photos)
                .writtenBy(author)
                .build();
        restaurants.getReviews().add(newReview);
        updateRestaurantAverageRating(restaurants);
        Restaurants savedRestaurants = restaurantRepository.save(restaurants);
        return savedRestaurants.getReviews().stream().filter(r -> reviewId.equals(r.getId())).findFirst().orElseThrow(() -> new RuntimeException("Review not found after saving"));
    }

    @Override
    public Page<Review> listReviews(String restaurantId, Pageable pageable) {
        Restaurants restaurants = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        List<Review> reviews = restaurants.getReviews();
        Sort sort = pageable.getSort();

        if (sort.isSorted()) {
            Sort.Order order = sort.iterator().next();
            String property = order.getProperty();
            boolean ascending = order.isAscending();
            switch (property) {
                case "postedDate" ->
                        reviews.sort((r1, r2) -> ascending ? r1.getPostedDate().compareTo(r2.getPostedDate()) : r2.getPostedDate().compareTo(r1.getPostedDate()));
                case "rating" ->
                        reviews.sort((r1, r2) -> ascending ? Integer.compare(r1.getRating(), r2.getRating()) : Integer.compare(r2.getRating(), r1.getRating()));
                default -> reviews.sort(Comparator.comparing(Review::getPostedDate).reversed());
            }


        } else {
            reviews.sort(Comparator.comparing(Review::getPostedDate).reversed());
        }
        int start = (int) pageable.getOffset();
        if (start >= reviews.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, reviews.size());
        }
        int end = Math.min(start + pageable.getPageSize(), reviews.size());
        return new PageImpl<>(reviews.subList(start, end), pageable, reviews.size());
    }

    @Override
    public Optional<Review> getReview(String restaurantId, String reviewId) {
        Restaurants restaurants= restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return restaurants.getReviews().stream().filter(r -> reviewId.equals(r.getId())).
                findFirst();

    }

    @Override
    public Review updateReview(User author, String restaurantId, String reviewId, ReviewCreateUpdateRequest review) {
        Restaurants restaurants= restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
            Review existingReview= restaurants.getReviews().stream()
                .filter(r -> reviewId.equals(r.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (!author.getId().equals(existingReview.getWrittenBy().getId())) {
            throw new ReviewNotAllowedException("User is not the author of this review");
        }
        if(LocalDateTime.now().isAfter(existingReview.getPostedDate().plusHours(48))) {
            throw new ReviewNotAllowedException("Review can only be edited within 48 hours of posting");
        }
        existingReview.setContent(review.getContent());
        existingReview.setRating(review.getRating());
        existingReview.setLastEdited(LocalDateTime.now());
        List<Photo> photos = review.getPhotoUrls().stream().map(url -> Photo.builder
                ().url(url).uploadedTime(LocalDateTime.now()).build()).toList();
        existingReview.setPhotos(photos);
        updateRestaurantAverageRating(restaurants);
        restaurantRepository.save(restaurants);
        return existingReview;
    }

    @Override
    public void deleteReview(User author, String restaurantId, String reviewId) {
        Restaurants restaurants= restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        Review existingReview= restaurants.getReviews().stream()
                .filter(r -> reviewId.equals(r.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (!author.getId().equals(existingReview.getWrittenBy().getId())) {
            throw new ReviewNotAllowedException("User is not the author of this review");
        }
        if(LocalDateTime.now().isAfter(existingReview.getPostedDate().plusHours(48))) {
            throw new ReviewNotAllowedException("Review can only be deleted within 48 hours of posting");
        }
        restaurants.getReviews().remove(existingReview);
        updateRestaurantAverageRating(restaurants);
    }


    //method to update average rating
    private void updateRestaurantAverageRating(Restaurants restaurants) {
        List<Review> reviews = restaurants.getReviews();
        if (reviews.isEmpty()) {
            restaurants.setAverageRating(0f);
        } else {
            float averageRating = (float) reviews.stream().mapToInt(Review::getRating).average().orElse(0);
            restaurants.setAverageRating(averageRating);
        }
        restaurantRepository.save(restaurants);
    }

}
