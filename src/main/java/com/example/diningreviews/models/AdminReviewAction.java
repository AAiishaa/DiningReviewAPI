package com.example.diningreviews.models;

import lombok.Getter;
import lombok.Setter;

// not an entity, holds the json the admin sends like true or false
@Getter
@Setter
public class AdminReviewAction {

    private boolean acceptReview;
}
