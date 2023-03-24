package com.hw.securitydemo4.repository;

import com.hw.securitydemo4.entry.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    List<Token> findAllByUserId(String id);

    Optional<Token> findByToken(String token);
}
