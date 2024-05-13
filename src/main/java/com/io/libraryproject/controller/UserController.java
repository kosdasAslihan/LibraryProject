package com.io.libraryproject.controller;

import com.io.libraryproject.dto.UserDTO;
import com.io.libraryproject.dto.request.AdminUserUpdateRequest;
import com.io.libraryproject.dto.request.UpdatePasswordRequest;
import com.io.libraryproject.dto.request.UserRequest;
import com.io.libraryproject.dto.response.LbResponse;
import com.io.libraryproject.dto.response.ResponseMessage;
import com.io.libraryproject.service.UserService;
import jakarta.validation.Valid;
import org.hibernate.annotations.Cache;
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
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<LbResponse> saveUser(@Valid @RequestBody UserRequest userRequest) {
        userService.saveUser(userRequest);

        LbResponse lbResponse = new LbResponse(ResponseMessage.USER_SAVED_RESPONSE_MESSAGE,true);
        return new ResponseEntity<>(lbResponse, HttpStatus.CREATED);
    }
    @GetMapping("/auth/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        List<UserDTO> listUserDTO = userService.getAllUser();

        return ResponseEntity.ok(listUserDTO);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<UserDTO> getUser(){
        UserDTO userDTO = userService.getPrincipal();
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping("/auth/pages")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Page<UserDTO>> getAllUserByPage(@RequestParam("page") int page,
                                                          @RequestParam("size") int size,
                                                          @RequestParam("sort") String prop,
                                                          @RequestParam(value = "direction", required = false,
                                                          defaultValue = "DESC")Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        Page<UserDTO> userDTOPage = userService.getAllUserWithPage(pageable);

        return ResponseEntity.ok(userDTOPage);
    }
    @PatchMapping("/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<LbResponse> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        userService.updatePassword(updatePasswordRequest);

        LbResponse lbResponse = new LbResponse(ResponseMessage.PASSWORD_UPDATED_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(lbResponse);
    }
    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<LbResponse> updateUserByAdmin(@PathVariable Long id,
                                                        @Valid @RequestBody AdminUserUpdateRequest adminUserUpdateRequest) {
        userService.updateUserByAdmin(id,adminUserUpdateRequest);
        LbResponse lbResponse = new LbResponse(ResponseMessage.USER_UPDATED_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(lbResponse);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<LbResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);

        LbResponse lbResponse = new LbResponse(ResponseMessage.USER_DELETED_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(lbResponse);
    }


}
