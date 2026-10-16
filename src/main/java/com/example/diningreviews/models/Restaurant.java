package com.example.diningreviews.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String name;
    private String zipCode;
    private String City;
    private String phoneNumber;
    private String website;
    // Wrapper type Double so they can be null when there are no reviews
    private Double overallScore;
    private Double peanutScore;
    private Double dairyScore;
    private Double eggScore;
}
