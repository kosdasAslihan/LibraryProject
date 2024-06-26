package com.io.libraryproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserUpdateRequest {

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private String birthDate;

    private String email;

    private String resetPasswordCode;
}
