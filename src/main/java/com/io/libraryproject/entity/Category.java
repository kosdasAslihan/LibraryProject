package com.io.libraryproject.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "t_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2,max = 80)
    @JsonProperty("categoryName")
    private String name;

    @NotNull
    private Boolean builtIn = false;

    private int sequence;

    @OneToMany(mappedBy = "categoryId")
    Set<Book> bookList = new HashSet<>();

    public static int sequenceValue = 999;


}
