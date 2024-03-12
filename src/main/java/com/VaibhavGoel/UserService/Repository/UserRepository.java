package com.VaibhavGoel.UserService.Repository;

import com.VaibhavGoel.UserService.Modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
