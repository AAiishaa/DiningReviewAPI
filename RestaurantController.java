
package com.example.diningreviews.controller;

import com.example.diningreviews.models.Restaurant;
import com.example.diningreviews.repository.RestaurantRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// marks the class as a controller
// tells Spring this class handles web requests and returns responses
@RestController

// sets the base URL for all the endpoints in this class
@RequestMapping("/restaurants")
public class RestaurantController {

    // controller needs the repository to access the database
    // dependency injection
    private final RestaurantRepository restaurantRepository;

    // constructor
    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // ADDING A RESTAURANT - POST

    // when someone sends a POST request to /restaurants, run the below method
    @PostMapping

    // return 201 Created if the method succeeds
    @ResponseStatus(HttpStatus.CREATED)

    // creating a method
    // @RequestBody: tells Spring to convert the incoming JSON into a Restaurant object
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {

        // check required fields
        if (restaurant.getName() == null || restaurant.getZipCode() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Name and Zipcode are required"
            );
        }

        // make sure there are no duplicates
        if (restaurantRepository
                .findByNameAndZipCode(
                        restaurant.getName(),
                        restaurant.getZipCode()
                ).isPresent()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Restaurant already exists"
            );
        }

        // resets fields a client shouldn't control
        // if someone tries adding these fields to the request
        restaurant.setId(null);
        restaurant.setPeanutScore(null);
        restaurant.setEggScore(null);
        restaurant.setDairyScore(null);

        // sends the restaurant to the repository to be saved in the database
        // then returns the saved restaurant to the client
        return restaurantRepository.save(restaurant);
    }

    // HANDLING GET REQUESTS
    // FINDING A RESTAURANT BY ID

    // {} used because the ID varies
    @GetMapping("/{id}")

    // creating a method
    // returns a Restaurant object
    public Restaurant getRestaurant(

            // PathVariable takes what is in the {id} placeholder
            // then passes the value to the method
            @PathVariable Long id) {

        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Restaurant not found"
                ));
    }

    // method for searching restaurants by zipcode and allergy
    // method returns List<Restaurant> because a search can return several restaurants

    @GetMapping
    public List<Restaurant> searchRestaurants(

            // Spring reads query parameters using @RequestParam
            @RequestParam String zipCode,
            @RequestParam String allergy) {

        // converts the allergy to lowercase and checks which allergy was requested
        switch (allergy.toLowerCase()) {

            case "peanut":
                // finds restaurants in the zip code with a peanut score,
                // ordered from highest score to lowest
                return restaurantRepository
                        .findByZipCodeAndPeanutScoreNotNullOrderByPeanutScoreDesc(zipCode);

            case "egg":
                // finds restaurants in the zip code with an egg score,
                // ordered from highest score to lowest
                return restaurantRepository
                        .findByZipCodeAndEggScoreNotNullOrderByEggScoreDesc(zipCode);

            case "dairy":
                // finds restaurants in the zip code with a dairy score,
                // ordered from highest score to lowest
                return restaurantRepository
                        .findByZipCodeAndDairyScoreNotNullOrderByDairyScoreDesc(zipCode);

            default:
                // returns an error if the allergy isn't supported
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Allergy must be peanut, egg, or dairy"
                );
        }
    }
}
