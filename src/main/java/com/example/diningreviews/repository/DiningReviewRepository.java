package com.example.diningreviews.repository;

import com.example.diningreviews.models.DiningReview;
import com.example.diningreviews.models.ReviewStatus;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DiningReviewRepository extends CrudRepository<DiningReview, Long> {

    List<DiningReview> findByStatus(ReviewStatus status);

    List<DiningReview> findByRestaurantIdAndStatus(Long restaurantId, ReviewStatus status);
}
