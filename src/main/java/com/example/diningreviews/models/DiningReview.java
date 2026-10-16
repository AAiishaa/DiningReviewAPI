
package com.example.diningreviews.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class DiningReview {

    // generate the ID automatically
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String submittedBy; // the user's display name
    private Long restaurantId;

    // optional scores (1-5) worst - best
    private Integer eggScore;
    private Integer dairyScore;
    private Integer peanutScore;

    private String comments;

    // save enum into the DB as text instead of a number
    // makes DB easy to read
    // prevents data corruption if i change the order of the enum later
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;
}
