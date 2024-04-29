package com.io.libraryproject.service;

import com.io.libraryproject.dto.request.UserRequest;
import com.io.libraryproject.entity.Role;
import com.io.libraryproject.exception.ConflictException;
import com.io.libraryproject.exception.message.ErrorMessage;
import com.io.libraryproject.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public void createUser(UserRequest userRequest) {
        Boolean existEmail = userRepository.existsByEmail(userRequest.getEmail());

        if (existEmail) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE));
        }

    }
}
