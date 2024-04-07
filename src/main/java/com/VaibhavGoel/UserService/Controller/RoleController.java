package com.VaibhavGoel.UserService.Controller;

import com.VaibhavGoel.UserService.DTO.CreateRoleRequestDto;
import com.VaibhavGoel.UserService.Modal.Role;
import com.VaibhavGoel.UserService.Services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    public RoleController(RoleService roleService){
        this.roleService=roleService;
    }
    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto createRoleRequestDto){
        Role role = roleService.createRole(createRoleRequestDto.getName());
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
