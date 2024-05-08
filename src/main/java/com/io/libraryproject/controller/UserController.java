package com.io.libraryproject.controller;

import com.io.libraryproject.dto.UserDTO;
import com.io.libraryproject.dto.request.UserRequest;
import com.io.libraryproject.dto.response.LbResponse;
import com.io.libraryproject.dto.response.ResponseMessage;
import com.io.libraryproject.service.UserService;
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
    @GetMapping("/users/all")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        List<UserDTO> listUserDTO = userService.getAllUser();

        return ResponseEntity.ok(listUserDTO);
    }
    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser(){
        UserDTO userDTO = userService.getPrincipal();
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUserByPage(@RequestParam("page") int page,
                                                          @RequestParam("size") int size,
                                                          @RequestParam("sort") String prop,
                                                          @RequestParam(value = "direction", required = false,
                                                          defaultValue = "DESC")Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        Page<UserDTO> userDTOPage = userService.getAllUserWithPage(pageable);

        return ResponseEntity.ok(userDTOPage);
    }
}
