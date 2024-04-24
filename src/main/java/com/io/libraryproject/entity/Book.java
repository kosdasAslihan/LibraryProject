package com.io.libraryproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="t_book")
public class Book {

    @Id
    private Long id;

    @NotNull
    @Size(min = 2,max = 80)
    @JsonProperty("bookName")
    private String name;

    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d{1}$")
    @Column(length = 17, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private Integer pageCount;

    @JoinColumn(name = "author_id", nullable = false)
    @ManyToOne
    private Author authorId;

    @JoinColumn(name = "publisher_id", nullable = false)
    @ManyToOne
    private Publisher publisherId;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private Integer publishDate;


    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne
    private Category categoryId;

    @NotNull
    private boolean loanable = true;

    @Column(length = 6, nullable = false)
    private String shelfCode;

    @NotNull
    private boolean active = false;

    @NotNull
    private boolean featured = false;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime createDate;

    @NotNull
    private Boolean builtIn = false;
}
