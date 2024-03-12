package com.VaibhavGoel.UserService.Controller;

import com.VaibhavGoel.UserService.DTO.*;
import com.VaibhavGoel.UserService.Modal.SessionStatus;
import com.VaibhavGoel.UserService.Services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService=authService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto requestDto){
        return authService.login(requestDto.getEmail(), requestDto.getPassword());
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto){
        return authService.logout(logoutRequestDto.getToken(), logoutRequestDto.getUserId());
    }
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        UserDto user = authService.signUp(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(ValidateTokenRequestDto validateTokenRequestDto){
        SessionStatus sessionStatus = authService.validate(validateTokenRequestDto.getToken(), validateTokenRequestDto.getUserId());
        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }
}
