package com.example.diningreviews.repository;
import com.example.diningreviews.models.User;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByDisplayName(String displayName);

}
