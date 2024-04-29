package com.io.libraryproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.io.libraryproject.entity.Book;
import com.io.libraryproject.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {

    private Long id;

    private User user;

    private Book book;

    private LocalDateTime loanDate = LocalDateTime.now();

    private LocalDateTime expireDate;

    private LocalDateTime returnDate;

    private String notes;
}
