package com.VaibhavGoel.UserService.Modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name="us_users")
@JsonDeserialize(as=User.class)
public class User extends BaseModal{
    private String email;
    private String password;
    @ManyToMany(fetch = jakarta.persistence.FetchType.EAGER)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();
}
