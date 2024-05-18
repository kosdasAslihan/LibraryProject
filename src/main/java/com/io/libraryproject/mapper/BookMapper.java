package com.io.libraryproject.mapper;

import com.io.libraryproject.dto.BookDTO;
import com.io.libraryproject.dto.request.BookRequest;
import com.io.libraryproject.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    List<BookDTO> mapBook(List<Book> getAllBooks);

    BookDTO bookToBookDTO(Book book);

}
