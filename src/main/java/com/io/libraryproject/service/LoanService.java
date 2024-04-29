package com.io.libraryproject.service;

import com.io.libraryproject.dto.request.LoanRequest;
import com.io.libraryproject.entity.User;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private final UserService userService;
    public LoanService(UserService userService) {
        this.userService = userService;
    }


}
