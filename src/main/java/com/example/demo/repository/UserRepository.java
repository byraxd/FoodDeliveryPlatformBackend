package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
    Optional<User> findById(Long id);
    User findByEmailAndPassword(String email, String password);

}
