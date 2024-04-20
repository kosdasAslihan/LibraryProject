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
    private Long id;

    @NotNull
    @Size(min = 2,max = 80)
    @JsonProperty("categoryName")
    private String name;

    @NotNull
    private Boolean builtIn = false;

    @NotNull
    private int sequence = 1000; //save category yapıldığı zaman sequence her seferinde 1 artsın

    @OneToMany(mappedBy = "categoryId")
    Set<Book> bookList = new HashSet<>();

}
