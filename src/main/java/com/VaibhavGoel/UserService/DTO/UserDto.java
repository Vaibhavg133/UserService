package com.VaibhavGoel.UserService.DTO;

import com.VaibhavGoel.UserService.Modal.Role;
import com.VaibhavGoel.UserService.Modal.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;
    private Set<Role> role = new HashSet<>();
    public static UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRoles());
        return userDto;
    }

}
