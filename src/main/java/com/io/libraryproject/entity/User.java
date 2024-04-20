package com.io.libraryproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.io.libraryproject.entity.enums.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="t_user")
public class User {

    @Id
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;

    @NotNull
    private Integer score = 0;

    @NotNull
    @Size(min = 10, max = 100)
    private String address;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please provide valid phone number")
    @Column(length = 10, nullable = false)
    private String phone;

    @NotNull
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate birthDate;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    private LocalDateTime createDate = LocalDateTime.now();

    @Column(nullable = false)
    private String resetPasswordCode;

    private Boolean builtIn = false ;

    @ManyToMany
    @JoinTable(name = "t_role_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles=new HashSet<>();
}

