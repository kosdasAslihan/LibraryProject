package com.io.libraryproject.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="t_author")
public class Author {

    @Id
    private Long id;

    @NotNull
    @Size(min = 4,max = 70)
    private String name;

    @NotNull
    private Boolean builtIn = false;

    @OneToMany(mappedBy = "authorId")
    Set<Book> bookList = new HashSet<>();
}
