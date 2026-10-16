
package com.example.diningreviews.controller;

import com.example.diningreviews.models.AdminReviewAction;
import com.example.diningreviews.models.DiningReview;
import com.example.diningreviews.models.Restaurant;
import com.example.diningreviews.models.ReviewStatus;
import com.example.diningreviews.repository.DiningReviewRepository;
import com.example.diningreviews.repository.RestaurantRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Function;

// this class handles web requests
@RestController

// all admin endpoints live under /admin
@RequestMapping("/admin")
public class AdminController {

    // commect to the repos
    private final DiningReviewRepository diningReviewRepository;
    private final RestaurantRepository restaurantRepository;

    // constructor
    public AdminController(
            DiningReviewRepository diningReviewRepository,
            RestaurantRepository restaurantRepository) {
        this.diningReviewRepository = diningReviewRepository;
        this.restaurantRepository = restaurantRepository;
    }

    // get all pending reviews
    // method returns a list of dining reviews, rather than just one
    // return reviews that are pending
    @GetMapping("/reviews")
    public List<DiningReview> getPendingReviews() {
        return diningReviewRepository.findByStatus(ReviewStatus.PENDING);
    }

    // accepting or rejecting a review - put

    @PutMapping("/reviews/{id}")
    public DiningReview reviewAction(
            @PathVariable Long id,
            @RequestBody AdminReviewAction action) {

        // find the review or return 404
        DiningReview review = diningReviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Review not found"
                ));

        // ternary operator: if true -> ACCEPTED, otherwise REJECTED
        review.setStatus(
                action.isAcceptReview()
                        ? ReviewStatus.ACCEPTED
                        : ReviewStatus.REJECTED
        );

        DiningReview saved = diningReviewRepository.save(review);

        // recalculate the restaurant's average scores
        updateRestaurantScores(saved.getRestaurantId());

        return saved;
    }

    // recalculates a restaurant's scores from all of its ACCEPTED reviews
    private void updateRestaurantScores(Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Restaurant not found"
                ));

        List<DiningReview> accepted = diningReviewRepository
                .findByRestaurantIdAndStatus(
                        restaurantId, ReviewStatus.ACCEPTED);

        // DiningReview::getPeanutScore is a method reference:
        // it tells average() which score to read from each review
        restaurant.setPeanutScore(
                average(accepted, DiningReview::getPeanutScore));
        restaurant.setEggScore(
                average(accepted, DiningReview::getEggScore));
        restaurant.setDairyScore(
                average(accepted, DiningReview::getDairyScore));

        restaurantRepository.save(restaurant);
    }

    // averages only the non-null scores
    // returns null if no review gave a score for that allergy
    private Double average(
            List<DiningReview> reviews,
            Function<DiningReview, Integer> scoreGetter) {

        OptionalDouble avg = reviews.stream()
                .map(scoreGetter)               // review -> its score
                .filter(Objects::nonNull)       // drop reviews that skipped this allergy
                .mapToInt(Integer::intValue)    // Integer -> int so average() works
                .average();

        if (avg.isPresent()) {
            return avg.getAsDouble();
        }
        return null;
    }
}
