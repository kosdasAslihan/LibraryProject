package com.io.libraryproject.service;

import com.io.libraryproject.entity.Role;
import com.io.libraryproject.entity.enums.RoleType;
import com.io.libraryproject.exception.ResourceNotFoundException;
import com.io.libraryproject.exception.message.ErrorMessage;
import com.io.libraryproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    public Role findByType(RoleType roleType) {
        Role role = roleRepository.findByRoleType(roleType).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_EXCEPTION,roleType,roleType.name())));
        return role;
    }
}
