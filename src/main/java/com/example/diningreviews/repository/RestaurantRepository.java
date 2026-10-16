package com.example.diningreviews.repository;
//RestaurantRepository is an interface for accessing restaurant data
// restaurant is the model it works with

//import restaurant model
import com.example.diningreviews.models.Restaurant;
import java.util.List;
import java.util.Optional;


//imports the repository interface
import org.springframework.data.repository.CrudRepository;



//interface inherits the database operations provided by CrudRepository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    //custom query methods for spring to create

    Optional<Restaurant> findByNameAndZipCode(String name, String zipCode);

    //Spring Data uses these instructions to generate the database query
    // findby: means this is a query
    // Name (this matched the attribute name in the restaurant entity (except the n)
    // And: combines the conditions


    //Spring Data interprets the method name to create the database query.
    // 2 sources of methods
    // Inherited methods: supplied through CrudRepository
    // Custom query methods: declared by you and implemented by Spring Data

    List<Restaurant> findByZipCodeAndPeanutScoreNotNullOrderByPeanutScoreDesc(String zipCode);

    List<Restaurant> findByZipCodeAndEggScoreNotNullOrderByEggScoreDesc(String zipCode);

    List<Restaurant> findByZipCodeAndDairyScoreNotNullOrderByDairyScoreDesc(String zipCode);

    //gives restaurants in a zip code that have a eg peanut score, with the best first

}
