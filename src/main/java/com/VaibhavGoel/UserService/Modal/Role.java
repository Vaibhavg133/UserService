package com.VaibhavGoel.UserService.Modal;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role extends BaseModal{
    private String role;
}
