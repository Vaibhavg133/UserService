package com.VaibhavGoel.UserService.Services;

import com.VaibhavGoel.UserService.Modal.Role;
import com.VaibhavGoel.UserService.Repository.RoleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class RoleService {
    private final RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
    public Role createRole(String name){
        Role role = new Role();
        role.setRole(name);
        return roleRepository.save(role);
    }
}
