package com.io.libraryproject.dto.request;

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
public class UserRequest {

    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;

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

    @Column(nullable = false)
    private String resetPasswordCode;

}
