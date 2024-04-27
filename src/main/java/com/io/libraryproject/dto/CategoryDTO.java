package com.io.libraryproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.io.libraryproject.entity.Book;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryDTO {

    private String name;

    private Boolean builtIn = false;

    private int sequence = 1000;

    Set<Book> bookList = new HashSet<>();
}
