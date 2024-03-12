package com.VaibhavGoel.UserService.Services;

import com.VaibhavGoel.UserService.DTO.UserDto;
import com.VaibhavGoel.UserService.Modal.Session;
import com.VaibhavGoel.UserService.Modal.SessionStatus;
import com.VaibhavGoel.UserService.Modal.User;
import com.VaibhavGoel.UserService.Repository.SessionRepository;
import com.VaibhavGoel.UserService.Repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    public AuthService(UserRepository userRepository, SessionRepository sessionRepository){
        this.userRepository=userRepository;
        this.sessionRepository=sessionRepository;
    }
    public ResponseEntity<UserDto> login(String email, String password){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return null;
        }
        User user = optionalUser.get();
        String token = RandomStringUtils.randomAlphanumeric(30);
        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);
        UserDto userdto = new UserDto();
        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);
        return new ResponseEntity<>(userdto, headers, HttpStatus.OK);
    }
    public ResponseEntity<Void> logout(String token, Long id){
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUserId(token,id);
        if(sessionOptional.isEmpty()){
            return null;
        }
        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
        return ResponseEntity.ok().build();
    }
    public UserDto signUp(String email, String password){
        Optional<User> optionalUserDto = userRepository.findByEmail(email);
        if(optionalUserDto.isPresent()){
            return null;
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }
    public SessionStatus validate(String token, Long userId){
        Optional<Session> optionalSession = sessionRepository.findByTokenAndUserId(token, userId);
        if(optionalSession.isEmpty()){
            return null;
        }
        return SessionStatus.ACTIVE;
    }
}
