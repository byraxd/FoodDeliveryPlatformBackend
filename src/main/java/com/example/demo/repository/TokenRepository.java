package com.example.demo.repository;

import com.example.demo.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, Long> {

    List<Token> findAllByUserIdAndLoggedOutFalse(Long userId);

    Optional<Token> findByToken(String token);

}
