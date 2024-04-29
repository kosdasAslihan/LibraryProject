package com.io.libraryproject.controller;

import com.io.libraryproject.dto.request.UserRequest;
import com.io.libraryproject.dto.response.LbResponse;
import com.io.libraryproject.dto.response.ResponseMessage;
import com.io.libraryproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<LbResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);

        LbResponse lbResponse = new LbResponse(ResponseMessage.USER_SAVED_RESPONSE_MESSAGE,true);
        return new ResponseEntity<>(lbResponse, HttpStatus.CREATED);
    }
}
