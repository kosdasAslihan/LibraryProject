package com.io.libraryproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.io.libraryproject.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private Integer score = 0;

    private String address;

    private String phone;

    private LocalDate birthDate;

    private String email;

    private String password;

    private LocalDateTime createDate = LocalDateTime.now();

    private String resetPasswordCode;

    private Boolean builtIn = false ;

    private Set<String> roles;

    public void setRoles(Set<Role> roles) {
        Set<String> roleStr = new HashSet<>();
        roles.forEach(r->{
            roleStr.add(r.getRoleType().getName());
        });
        this.roles = roleStr;
    }
}
