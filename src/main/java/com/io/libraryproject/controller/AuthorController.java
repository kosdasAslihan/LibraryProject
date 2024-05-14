package com.io.libraryproject.controller;

import com.io.libraryproject.dto.AuthorDTO;
import com.io.libraryproject.dto.CategoryDTO;
import com.io.libraryproject.dto.request.AuthorRequest;
import com.io.libraryproject.dto.response.LbResponse;
import com.io.libraryproject.dto.response.ResponseMessage;
import com.io.libraryproject.service.AuthorService;
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
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LbResponse> saveAuthor (@Valid @RequestBody AuthorRequest authorRequest) {
        authorService.saveAuthor(authorRequest);

        LbResponse lbResponse = new LbResponse(ResponseMessage.AUTHOR_SAVED_RESPONSE_MESSAGE,true);
        return new ResponseEntity<>(lbResponse, HttpStatus.CREATED);
    }
    @GetMapping("/visitors/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<List<AuthorDTO>> getAllAuthor(){
        List<AuthorDTO> authorDTOList = authorService.getAllAuthor();
        return ResponseEntity.ok(authorDTOList);
    }
    @GetMapping("/visitors/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO authorDTO = authorService.getAuthorById(id);

        return ResponseEntity.ok(authorDTO);
    }
    @GetMapping("/visitors/pages")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<Page<AuthorDTO>> getAllAuthorByPage(@RequestParam("page") int page,
                                                              @RequestParam("size") int size,
                                                              @RequestParam("sort") String prop,
                                                              @RequestParam(value = "direction", required = false,
                                                                      defaultValue = "DESC") Sort.Direction direction){
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<AuthorDTO> allAuthorByPage = authorService.getAllAuthorByPage(pageable);

        return ResponseEntity.ok(allAuthorByPage);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LbResponse> updateAuthor(@PathVariable Long id,
                                                   @Valid @RequestBody AuthorRequest authorRequest) {
        authorService.updateAuthor(id, authorRequest);

        LbResponse lbResponse = new LbResponse(ResponseMessage.AUTHOR_UPDATED_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(lbResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LbResponse> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);

        LbResponse lbResponse = new LbResponse(ResponseMessage.AUTHOR_DELETED_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(lbResponse);
    }

}
