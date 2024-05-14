package com.io.libraryproject.dto.response;

import com.io.libraryproject.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {

    public Long id;
    public Long userId;
    public BookDTO bookId;
}
