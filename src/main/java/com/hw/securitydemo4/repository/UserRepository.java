package com.hw.securitydemo4.repository;

import java.util.Optional;

import com.hw.securitydemo4.entry.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

}
