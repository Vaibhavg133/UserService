package com.VaibhavGoel.UserService.Services;

import com.VaibhavGoel.UserService.DTO.UserDto;
import com.VaibhavGoel.UserService.Modal.Role;
import com.VaibhavGoel.UserService.Modal.Session;
import com.VaibhavGoel.UserService.Modal.SessionStatus;
import com.VaibhavGoel.UserService.Modal.User;
import com.VaibhavGoel.UserService.Repository.SessionRepository;
import com.VaibhavGoel.UserService.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;
import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository;
        this.sessionRepository=sessionRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }
    public ResponseEntity<UserDto> login(String email, String password){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return null;
        }
        User user = optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
           throw new RuntimeException("Wrong Password Entered");
        }
        Optional<Integer> sessionCount = sessionRepository.findSessionCount(user.getId());
        if(sessionCount.isPresent() && sessionCount.get()>=2){
            return null;
        }
        //String token = RandomStringUtils.randomAlphanumeric(30);
        //JWTS Token Creation
        MacAlgorithm algorithm = Jwts.SIG.HS256;
        SecretKey secretKey = algorithm.key().build();
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("email",user.getEmail());
        jsonMap.put("roles",List.of(user.getRoles()));
        jsonMap.put("createdAt",new Date());
        jsonMap.put("expiryAt", DateUtils.addDays(new Date(),30));
        String jws = Jwts.builder().claims(jsonMap).signWith(secretKey,algorithm).compact();
        //JWS Token Creation End
        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(jws);
        session.setUser(user);
        session.setExpiryAt(DateUtils.addDays(new Date(),30));
        sessionRepository.save(session);
        UserDto userdto = new UserDto();
        userdto.setEmail(user.getEmail());
        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + jws);
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
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }
    public SessionStatus validate(String token, Long userId){
        Optional<Session> optionalSession = sessionRepository.findByTokenAndUserId(token, userId);
        if(optionalSession.isEmpty()){
            return null;
        }
        Session session = optionalSession.get();
        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE)){
            return SessionStatus.ENDED;
        }

        //JWT Token

        Jws<Claims> jwsClaims = Jwts.parser().build().parseSignedClaims(token);
        String email = (String) jwsClaims.getPayload().get("email");
        List<Role> roles = (List<Role>) jwsClaims.getPayload().get("roles");
        Date createdAt = (Date) jwsClaims.getPayload().get("createdAt");

        return optionalSession.get().getSessionStatus();
    }
}
