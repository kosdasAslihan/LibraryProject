package com.io.libraryproject.service;

import com.io.libraryproject.dto.BookDTO;
import com.io.libraryproject.dto.request.BookRequest;
import com.io.libraryproject.entity.Book;
import com.io.libraryproject.exception.ResourceNotFoundException;
import com.io.libraryproject.exception.message.ErrorMessage;
import com.io.libraryproject.mapper.BookMapper;
import com.io.libraryproject.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }
    public void createBook(BookRequest bookRequest) {
        Book book = new Book();
        book.setName(bookRequest.getName());

        bookRepository.save(book);
    }
    public List<BookDTO> getAllBook() {
        List<Book> getAllBooks = bookRepository.findAll();
        return bookMapper.mapBook(getAllBooks);
    }
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.BOOK_NOT_FOUND_EXCEPTION,id)));
        return bookMapper.bookToBookDTO(book);
    }
    public Page<BookDTO> getAllBookByPage(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        return books.map(bookMapper::bookToBookDTO);

    }


}
