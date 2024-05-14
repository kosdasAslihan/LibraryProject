package com.io.libraryproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {

    private String name;

    private String isbn;

    private Integer pageCount;

    private Long authorId;

    private Long publisherId;

    private Long categoryId;

    private Integer publishDate;

    private String shelfCode;

    private boolean featured;

    private boolean active;

    private boolean loanable;
}
