package com.io.libraryproject.dto;

import com.io.libraryproject.entity.Author;
import com.io.libraryproject.entity.Category;
import com.io.libraryproject.entity.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookDTO {

    private Long id;

    private String name;

    private String isbn;

    private Integer pageCount;

    private Integer publishDate;

    private String shelfCode;

    private boolean active=true;

    private boolean featured=false;

    private boolean loanable=true;

    private LocalDateTime createDate ;

    private boolean builtIn =false;

    private Category category;

    private Publisher publisher;

    private Author author;

}
