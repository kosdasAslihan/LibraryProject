package com.io.libraryproject.controller;

import com.io.libraryproject.dto.AuthorDTO;
import com.io.libraryproject.dto.BookDTO;
import com.io.libraryproject.dto.request.BookRequest;
import com.io.libraryproject.dto.response.LbResponse;
import com.io.libraryproject.dto.response.ResponseMessage;
import com.io.libraryproject.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LbResponse> createBook(@Valid @RequestBody BookRequest bookRequest) {
        bookService.createBook(bookRequest);

        LbResponse lbResponse = new LbResponse(ResponseMessage.BOOK_CREATED_RESPONSE_MESSAGE,true);
        return new ResponseEntity<>(lbResponse, HttpStatus.CREATED);
    }
    @GetMapping("/visitors/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<List<BookDTO>> getAllBook(){
        List<BookDTO> bookDTOList = bookService.getAllBook();
        return ResponseEntity.ok(bookDTOList);
    }
    @GetMapping("/visitors/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO bookDTO = bookService.getBookById(id);

        return ResponseEntity.ok(bookDTO);
    }
    @GetMapping("/visitors/pages")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<Page<BookDTO>> getAllBookByPage(@RequestParam("page") int page,
                                                          @RequestParam("size") int size,
                                                          @RequestParam("sort") String prop,
                                                          @RequestParam(value = "direction", required = false,
                                                          defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        Page<BookDTO> allBookByPage = bookService.getAllBookByPage(pageable);

        return ResponseEntity.ok(allBookByPage);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LbResponse> updateBook (@RequestParam("id") Long id,
                                                  @Valid @RequestBody BookRequest bookRequest) {
        bookService.updateBook(id,bookRequest);
        LbResponse lbResponse = new LbResponse(ResponseMessage.BOOK_UPDATED_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(lbResponse);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LbResponse> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);

        LbResponse lbResponse = new LbResponse(ResponseMessage.BOOK_DELETED_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(lbResponse);
    }




}
