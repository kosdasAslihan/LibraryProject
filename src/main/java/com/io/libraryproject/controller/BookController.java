package com.io.libraryproject.controller;

import com.io.libraryproject.dto.request.BookRequest;
import com.io.libraryproject.dto.response.ClientResponse;
import com.io.libraryproject.dto.response.ResponseMessage;
import com.io.libraryproject.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {



}
