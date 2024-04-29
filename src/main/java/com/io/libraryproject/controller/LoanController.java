package com.io.libraryproject.controller;

import com.io.libraryproject.dto.request.LoanRequest;
import com.io.libraryproject.dto.response.LbResponse;
import com.io.libraryproject.dto.response.ResponseMessage;
import com.io.libraryproject.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
    @PostMapping
    public ResponseEntity<LbResponse> createLoan(@Valid @RequestBody LoanRequest loanRequest) {
        loanService.saveLoan(loanRequest);

        LbResponse lbResponse = new LbResponse(ResponseMessage.LOAN_SAVED_RESPONSE_MESSAGE,true);
        return new ResponseEntity<>(lbResponse, HttpStatus.CREATED);
    }
}
