package com.io.libraryproject.dto;

import com.io.libraryproject.entity.Book;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {

    private String name;

    private Boolean builtIn = false;

    Set<Book> bookList = new HashSet<>();
}
