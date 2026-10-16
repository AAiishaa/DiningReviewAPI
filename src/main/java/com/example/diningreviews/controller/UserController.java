
package com.example.diningreviews.controller;

import com.example.diningreviews.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.diningreviews.repository.UserRepository;


// tells spring this class handles web requests
@RestController

// all user endpoints live under /users
@RequestMapping("/users")
public class UserController {

    // connects this controller to the user database
    //declare a variable to hold the repository
    private final UserRepository userRepository;

    // constructor
    public UserController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    // registering a user

    @PostMapping // handles post requests
    @ResponseStatus(HttpStatus.CREATED)

    // convert the JSON sent by the client into a Java User object
    public User addUser(@RequestBody User user) {

        // check that the required fields have been provided
        if (user.getDisplayName() == null
                || user.getDisplayName().isBlank()
                || user.getCity() == null
                || user.getZipCode() == null
                || user.getState() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Display name, city, zip code and state are required"
            );
        }

        // check whether the display name is already being used
        if (userRepository.findByDisplayName(user.getDisplayName())
                .isPresent()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Display name already exists"
            );
        }

        // make sure the client cannot choose their own database ID
        user.setId(null);

        // save the user and return the saved details
        return userRepository.save(user);
    }

    // finding a user - get /users/{displayName}

    // {} placeholder
    @GetMapping("/{displayName}")

    //@PathVariable extracts the value from the URL and passes it into the method
    public User getUser(@PathVariable String displayName) {

        // find the user by display name or return a 404 error
        return userRepository.findByDisplayName(displayName)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
    }
}
