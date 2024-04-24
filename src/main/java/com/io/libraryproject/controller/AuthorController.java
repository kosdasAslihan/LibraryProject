package com.io.libraryproject.controller;

import com.io.libraryproject.dto.AuthorDTO;
import com.io.libraryproject.dto.request.AuthorRequest;
import com.io.libraryproject.dto.response.ClientResponse;
import com.io.libraryproject.dto.response.ResponseMessage;
import com.io.libraryproject.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ClientResponse> saveAuthor (@Valid @RequestBody AuthorRequest authorRequest) {
        authorService.saveAuthor(authorRequest);

        ClientResponse clientResponse = new ClientResponse(ResponseMessage.AUTHOR_SAVED_RESPONSE_MESSAGE,true);
        return new ResponseEntity<>(clientResponse, HttpStatus.CREATED);
    }
    @GetMapping("/visitors/all")
    public ResponseEntity<List<AuthorDTO>> getAllAuthor() {
        List<AuthorDTO> authorDTOS = authorService.getAllAuthor();

        return ResponseEntity.ok(authorDTOS);
    }
    @GetMapping("/visitors/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO authorDTO = authorService.getAuthorById(id);

        return ResponseEntity.ok(authorDTO);
    }
    @GetMapping("/visitors/pages")
    public ResponseEntity<Page<AuthorDTO>> getAllAuthorByPage(@RequestParam("page") int page,
                                                              @RequestParam("size") int size,
                                                              @RequestParam("sort") String prop,
                                                              @RequestParam(value = "direction", required = false,
                                                                      defaultValue = "DESC") Sort.Direction direction){
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<AuthorDTO> allAuthorByPage = authorService.getAllAuthorByPage(pageable);

        return ResponseEntity.ok(allAuthorByPage);
    }

}
