package com.VaibhavGoel.UserService.Controller;

import com.VaibhavGoel.UserService.DTO.SetUserRoleRequestDto;
import com.VaibhavGoel.UserService.DTO.UserDto;
import com.VaibhavGoel.UserService.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long id){
        UserDto user = userService.getUserDetails(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long id, @RequestBody SetUserRoleRequestDto setUserRoleRequestDto){
        UserDto user = userService.setUserRoles(id,setUserRoleRequestDto.getRoleIds());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
