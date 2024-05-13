package com.io.libraryproject.service;

import com.io.libraryproject.dto.UserDTO;
import com.io.libraryproject.dto.request.AdminUserUpdateRequest;
import com.io.libraryproject.dto.request.RegisterRequest;
import com.io.libraryproject.dto.request.UpdatePasswordRequest;
import com.io.libraryproject.dto.request.UserRequest;
import com.io.libraryproject.entity.Loan;
import com.io.libraryproject.entity.Role;
import com.io.libraryproject.entity.User;
import com.io.libraryproject.entity.enums.RoleType;
import com.io.libraryproject.exception.BadRequestException;
import com.io.libraryproject.exception.ConflictException;
import com.io.libraryproject.exception.ResourceNotFoundException;
import com.io.libraryproject.exception.message.ErrorMessage;
import com.io.libraryproject.mapper.UserMapper;
import com.io.libraryproject.repository.LoanRepository;
import com.io.libraryproject.repository.UserRepository;
import com.io.libraryproject.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final LoanRepository loanRepository;
    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, UserMapper userMapper, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.loanRepository = loanRepository;
    }

    public void saveUser(@Valid UserRequest registerRequest) {
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
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        User user = getCurrentUser();

        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessage.PASSWORD_NOT_MATCHED_MESSAGE);
        }

        String hashedPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }
    @Transactional
    public void updateUserByAdmin(Long id, AdminUserUpdateRequest adminUserUpdateRequest) {
        User user = getCurrentUser();

        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        boolean existEmail = userRepository.existsByEmail(adminUserUpdateRequest.getEmail());
        if (existEmail && !adminUserUpdateRequest.getEmail().equals(user.getEmail())) {
            throw new BadRequestException(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE);
        }
        userRepository.update(user.getId(),
                adminUserUpdateRequest.getFirstName(),
                adminUserUpdateRequest.getLastName(),
                adminUserUpdateRequest.getAddress(),
                adminUserUpdateRequest.getPhone(),
                adminUserUpdateRequest.getBirthDate(),
                adminUserUpdateRequest.getEmail(),
                adminUserUpdateRequest.getResetPasswordCode());
    }

    public User getById(Long id) {
        User user = userRepository.findUserById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));
        return user;
    }
    public void deleteUserById(Long id) {
        User user = getById(id);

        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        boolean existsByUser = loanRepository.existsByUser(user);
        List<Loan> loanList = loanRepository.findAllByUserId(id);

        if (existsByUser) {
            int count = 0;
            for (Loan w : loanList) {
                if (w.getReturnDate() == null) {
                    count++;
                }
            }
            if (count > 0) {
                throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
            }
        }

        userRepository.deleteById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
