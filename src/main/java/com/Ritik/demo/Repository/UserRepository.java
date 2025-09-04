package com.Ritik.demo.Repository;

import com.Ritik.demo.User.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<user,Long> {
    Optional<user> findByEmail(String email);
    Optional<user> findByUsername(String username);
}
