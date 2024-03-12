package com.VaibhavGoel.UserService.Repository;

import com.VaibhavGoel.UserService.Modal.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByTokenAndUserId(String Id, Long id);
}
