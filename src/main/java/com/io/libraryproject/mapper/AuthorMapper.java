package com.io.libraryproject.mapper;

import com.io.libraryproject.dto.AuthorDTO;
import com.io.libraryproject.entity.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    List<AuthorDTO> authorMap(List<Author> authors);

    AuthorDTO authorToAuthorDTO(Author author);
}
