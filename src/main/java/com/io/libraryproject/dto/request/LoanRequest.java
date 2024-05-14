package com.io.libraryproject.dto.request;

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
public class LoanRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long bookId;

    @NotNull
    private String notes;
}
