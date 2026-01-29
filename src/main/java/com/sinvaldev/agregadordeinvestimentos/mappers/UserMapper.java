package com.sinvaldev.agregadordeinvestimentos.mappers;

import com.sinvaldev.agregadordeinvestimentos.dtos.user.RequestUserDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.user.ResponseUserDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.user.UserDto;
import com.sinvaldev.agregadordeinvestimentos.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto (User user);

    User userDtotoUser (UserDto userDto);

    RequestUserDto userToRequestUserDto(User user);

    User requestUserDtoToUser (RequestUserDto requestUserDto);

    UserDto responseUserDtoToUserDto(ResponseUserDto responseUserDto);

    ResponseUserDto userDtoToResponseUserDto(UserDto userDto);
}
