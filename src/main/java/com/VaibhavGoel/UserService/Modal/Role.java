package com.VaibhavGoel.UserService.Modal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="us_role")
@JsonDeserialize(as=Role.class)
public class Role extends BaseModal{
    private String role;
}
