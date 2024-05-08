package com.io.libraryproject.service;

import com.io.libraryproject.dto.UserDTO;
import com.io.libraryproject.dto.request.RegisterRequest;
import com.io.libraryproject.dto.request.UserRequest;
import com.io.libraryproject.entity.Role;
import com.io.libraryproject.entity.User;
import com.io.libraryproject.entity.enums.RoleType;
import com.io.libraryproject.exception.ConflictException;
import com.io.libraryproject.exception.ResourceNotFoundException;
import com.io.libraryproject.exception.message.ErrorMessage;
import com.io.libraryproject.mapper.UserMapper;
import com.io.libraryproject.repository.UserRepository;
import com.io.libraryproject.security.SecurityUtils;
import jakarta.validation.Valid;
import org.mapstruct.control.MappingControl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public void createUser(@Valid UserRequest registerRequest) {
        Boolean existEmail = userRepository.existsByEmail(registerRequest.getEmail());

        if (existEmail) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE));
        }
        Role role = roleService.findByType(RoleType.ROLE_MEMBER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        String password = registerRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setAddress(registerRequest.getAddress());
        user.setPhone(registerRequest.getPhone());
        user.setBirthDate(registerRequest.getBirthDate());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setResetPasswordCode(registerRequest.getResetPasswordCode());
        user.setRoles(roles);

        userRepository.save(user);

    }
    public void saveUser(@Valid RegisterRequest registerRequest) {
        Boolean existEmail = userRepository.existsByEmail(registerRequest.getEmail());

        if (existEmail) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE));
        }
        Role role = roleService.findByType(RoleType.ROLE_MEMBER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        String password = registerRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setAddress(registerRequest.getAddress());
        user.setPhone(registerRequest.getPhone());
        user.setBirthDate(registerRequest.getBirthDate());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setResetPasswordCode(registerRequest.getResetPasswordCode());

        userRepository.save(user);

    }
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        return user;
    }
    public List<UserDTO> getAllUser() {
        List<User> userList = userRepository.findAll();

        return userMapper.userListToUserDtoList(userList);
    }
    public UserDTO getPrincipal() {
        User user = getCurrentUser();
        UserDTO userDTO = userMapper.userToUserDto(user);
        return userDTO;
    }
    private User getCurrentUser() {
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow(()->
                new ResourceNotFoundException(ErrorMessage.PRINCIPAL_FOUND_MESSAGE));
        User user = getUserByEmail(email);
        return user;
    }
    public UserDTO getUserById(Long id) {
        User user = userRepository.findUserById(id).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND_EXCEPTION));
        UserDTO userDTO = userMapper.userToUserDto(user);
        return userDTO;
    }
    public Page<UserDTO> getAllUserWithPage(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return getUserByPage(userPage);
    }
    private Page<UserDTO> getUserByPage(Page<User> userPage) {
        return userPage.map(userMapper::userToUserDto);
    }
}
