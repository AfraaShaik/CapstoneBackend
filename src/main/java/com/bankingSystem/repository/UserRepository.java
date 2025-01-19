package com.bankingSystem.repository;

import com.bankingSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//it represents the Data access layer for the User entity.
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
