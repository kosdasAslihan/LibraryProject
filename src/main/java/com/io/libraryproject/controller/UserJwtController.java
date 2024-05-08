package com.io.libraryproject.controller;

import com.io.libraryproject.dto.request.LoginRequest;
import com.io.libraryproject.dto.request.RegisterRequest;
import com.io.libraryproject.dto.request.UserRequest;
import com.io.libraryproject.dto.response.LbResponse;
import com.io.libraryproject.dto.response.LoginResponse;
import com.io.libraryproject.dto.response.ResponseMessage;
import com.io.libraryproject.security.jwt.JwtUtils;
import com.io.libraryproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserJwtController {
    private final JwtUtils jwtUtils;


    private final UserService userService;


    private final AuthenticationManager authenticationManager;

    public UserJwtController(JwtUtils jwtUtils, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/register")
    public ResponseEntity<LbResponse> registerUser(@Valid @RequestBody UserRequest registerRequest) {
        userService.createUser(registerRequest);

        LbResponse libResponse = new LbResponse(ResponseMessage.REGISTER_RESPONSE_MESSAGE, true);
        return new ResponseEntity<>(libResponse, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());//Kullanıcı bilgilerini zarfladık AutheManager Türüne çevirdik

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);//Authentice edilmiş kullanıcı oluşturuldu

        //Token üretimine geçildi
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();//Anlık login yapan kullanıcı bilgilerini getirdi

        String jwtToken = jwtUtils.generateJwtToken(userDetails);//token oluştu.

        LoginResponse loginResponse = new LoginResponse(jwtToken);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);

    }
}
