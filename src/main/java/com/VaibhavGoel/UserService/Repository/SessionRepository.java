package com.VaibhavGoel.UserService.Repository;

import com.VaibhavGoel.UserService.Modal.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByTokenAndUserId(String Id, Long id);
    @Query(value = "SELECT count(token) from public.us_session where user_id=?1",nativeQuery = true)
    Optional<Integer> findSessionCount(Long userId);

}
