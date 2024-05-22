package com.io.libraryproject.service;

import com.io.libraryproject.dto.BookDTO;
import com.io.libraryproject.dto.request.BookRequest;
import com.io.libraryproject.entity.*;
import com.io.libraryproject.exception.BadRequestException;
import com.io.libraryproject.exception.ConflictException;
import com.io.libraryproject.exception.ResourceNotFoundException;
import com.io.libraryproject.exception.message.ErrorMessage;
import com.io.libraryproject.mapper.BookMapper;
import com.io.libraryproject.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final CategoryService categoryService;
    private final LoanService loanService;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, AuthorService authorService, PublisherService publisherService, CategoryService categoryService, LoanService loanService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.categoryService = categoryService;
        this.loanService = loanService;
    }
    public void createBook(BookRequest bookRequest) {
        Book book = new Book();

        boolean existByIsbn=bookRepository.existsByIsbn(bookRequest.getIsbn());
        if (existByIsbn){
            throw new ConflictException(ErrorMessage.ISBN_ALREADY_EXIST_MESSAGE);
        }
        Author author=authorService.getAuthorByIdForBook(bookRequest.getAuthorId());
        Publisher publisher=publisherService.getPublisher(bookRequest.getPublisherId());
        Category category=categoryService.findCategoryById(bookRequest.getCategoryId());

        book.setAuthor(author);
        book.setCategory(category);
        book.setPublisher(publisher);
        book.setName(bookRequest.getName());
        book.setIsbn(bookRequest.getIsbn());
        book.setPageCount(bookRequest.getPageCount());
        book.setPublishDate(bookRequest.getPublishDate());
        book.setShelfCode(bookRequest.getShelfCode());
        book.setFeatured(bookRequest.isFeatured());
        book.setCreateDate(LocalDateTime.now());

        bookRepository.save(book);
    }
    private BookDTO bookToBookDTO(Book book) {
        return BookDTO.builder().
                id(book.getId()).
                name(book.getName()).
                isbn(book.getIsbn()).
                pageCount(book.getPageCount()).
                author(book.getAuthor()).
                publisher(book.getPublisher()).
                shelfCode(book.getShelfCode()).
                active(book.isActive()).
                builtIn(book.isBuiltIn()).
                loanable(book.isLoanable()).
                createDate(LocalDateTime.now()).
                featured(book.isFeatured()).
                build();
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
    public Book getBook(Long id) {
        return bookRepository.findBookById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.BOOK_NOT_FOUND_EXCEPTION,id)));
    }
    public void updateBook(Long id, BookRequest bookRequest) {
        Book book = getBook(id);

        if (book.isBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        boolean existByIsbn = bookRepository.existsByIsbn(bookRequest.getIsbn());
        if (existByIsbn && !bookRequest.getIsbn().equals(book.getIsbn())){
            throw new ConflictException(ErrorMessage.ISBN_ALREADY_EXIST_MESSAGE);
        }

        Author author = authorService.getAuthorByIdForBook(bookRequest.getAuthorId());
        Publisher publisher = publisherService.getPublisher(bookRequest.getPublisherId());
        Category category = categoryService.findCategoryById(bookRequest.getCategoryId());

        book.setAuthor(author);
        book.setCategory(category);
        book.setPublisher(publisher);
        book.setName(bookRequest.getName());
        book.setIsbn(bookRequest.getIsbn());
        book.setPageCount(bookRequest.getPageCount());
        book.setPublishDate(bookRequest.getPublishDate());
        book.setShelfCode(bookRequest.getShelfCode());
        book.setFeatured(bookRequest.isFeatured());
        book.setCreateDate(LocalDateTime.now());

        bookRepository.save(book);
    }
    public void deleteBook(Long id) {
        Book book = getBook(id);

        if (book.isBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        List<Loan> loans = loanService.findByBookId(book.getId());
        if (loans != null) {
            for (Loan w : loans) {
                if (w.getReturnDate() == null) {
                    throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
                }
            }
        }
        bookRepository.delete(book);
    }
    public void save(Book book) {
        bookRepository.save(book);
    }
}
