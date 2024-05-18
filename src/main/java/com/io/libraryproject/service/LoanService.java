package com.io.libraryproject.service;

import com.io.libraryproject.dto.request.LoanRequest;
import com.io.libraryproject.dto.response.LoanResponse;
import com.io.libraryproject.entity.Book;
import com.io.libraryproject.entity.Loan;
import com.io.libraryproject.entity.User;
import com.io.libraryproject.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;

    }

    public List<Loan> findByBookId(Long id) {
        List<Loan> loans = loanRepository.findByBookId(id);
        return loans;
    }
}
