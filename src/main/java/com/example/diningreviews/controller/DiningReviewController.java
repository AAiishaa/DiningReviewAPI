package com.example.diningreviews.controller;

import com.example.diningreviews.models.DiningReview;
import com.example.diningreviews.models.ReviewStatus;
import com.example.diningreviews.repository.DiningReviewRepository;
import com.example.diningreviews.repository.RestaurantRepository;
import com.example.diningreviews.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


//tells Spring Boot that this class handles web requests and sends responses back
@RestController

//sets the base URL for the endpoints in this controller
@RequestMapping("/reviews")
public class DiningReviewController {

    // declare variables that holds the repositories
    private final DiningReviewRepository diningReviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    //constructor - stores repository
    public DiningReviewController(
            DiningReviewRepository diningReviewRepository,
            UserRepository userRepository,
            RestaurantRepository restaurantRepository) {

        this.diningReviewRepository = diningReviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }


    // Submit a new dining review

    @PostMapping // handles POST requests to /reviews
    @ResponseStatus(HttpStatus.CREATED)

    //method that returns a dining review
    public DiningReview submitReview(@RequestBody DiningReview review) {

        // Check that the submitting user exists
        if (review.getSubmittedBy() == null
                || userRepository.findByDisplayName(
                review.getSubmittedBy()).isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Submitting user does not exist"
            );
        }

        // Check that the restaurant exists
        if (review.getRestaurantId() == null
                || !restaurantRepository.existsById(
                review.getRestaurantId())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Restaurant does not exist"
            );
        }

        // Validate scores (scores are optional, but must be 1–5)
        validateScore(review.getPeanutScore());
        validateScore(review.getEggScore());
        validateScore(review.getDairyScore());

        // The server assigns the ID and sets pending status
        review.setId(null);
        review.setStatus(ReviewStatus.PENDING);

        return diningReviewRepository.save(review);
    }

    // Get a review by its ID
    @GetMapping("/{id}")
    public DiningReview getReview(@PathVariable Long id) {

        return diningReviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Review not found"
                ));
    }

    // Validate that a score is between 1 and 5
    private void validateScore(Integer score) {

        if (score != null && (score < 1 || score > 5)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Scores must be between 1 and 5"
            );
        }
    }
}