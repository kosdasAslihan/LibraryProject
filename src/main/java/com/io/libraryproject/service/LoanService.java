package com.io.libraryproject.service;

import com.io.libraryproject.dto.request.LoanRequest;
import com.io.libraryproject.dto.response.LoanResponse;
import com.io.libraryproject.entity.Book;
import com.io.libraryproject.entity.User;
import com.io.libraryproject.repository.LoanRepository;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserService userService;
    private final BookService bookService;
    public LoanService(LoanRepository loanRepository, UserService userService, BookService bookService) {
        this.loanRepository = loanRepository;
        this.userService = userService;
        this.bookService = bookService;
    }
}
