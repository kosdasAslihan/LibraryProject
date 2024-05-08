package com.io.libraryproject.mapper;

import com.io.libraryproject.dto.UserDTO;
import com.io.libraryproject.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDTO> userListToUserDtoList(List<User> userList);

    UserDTO userToUserDto(User user);
}
