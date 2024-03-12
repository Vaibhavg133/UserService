package com.VaibhavGoel.UserService.Services;

import com.VaibhavGoel.UserService.DTO.UserDto;
import com.VaibhavGoel.UserService.Modal.Role;
import com.VaibhavGoel.UserService.Modal.User;
import com.VaibhavGoel.UserService.Repository.RoleRepository;
import com.VaibhavGoel.UserService.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    public UserService(RoleRepository roleRepository,UserRepository userRepository){
        this.roleRepository=roleRepository;
        this.userRepository=userRepository;
    }
    public UserDto getUserDetails(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            return null;
        }
        return UserDto.from(optionalUser.get());
    }
    public UserDto setUserRoles(Long userId, List<Long> roleIds){
        Optional<User> optionalUser = userRepository.findById(userId);
        List<Role> roles = roleRepository.findAllByIdIn(roleIds);
        if(optionalUser.isEmpty()){return null;}
        User user = new User();
        user.setRoles(Set.copyOf(roles));
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }
}
