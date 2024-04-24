package com.io.libraryproject.repository;

import com.io.libraryproject.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    //Optional<Author> finAuthorById(Long id);
}
