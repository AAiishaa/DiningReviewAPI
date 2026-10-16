package com.example.diningreviews.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "app_user")   // "user" is a reserved word in H2
@Getter
@Setter
public class User {

    // Long can store numerical number and null value

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String displayName;

    private String city;
    private String zipCode;
    private String state;

    private boolean InterestedInPeanut;
    private boolean InterestedInDairy;
    private boolean InterestedInEgg;



}
