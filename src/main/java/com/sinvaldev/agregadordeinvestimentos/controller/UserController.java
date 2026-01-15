package com.sinvaldev.agregadordeinvestimentos.controller;

import com.sinvaldev.agregadordeinvestimentos.dtos.RequestUserDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.ResponseUserDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.UserDto;
import com.sinvaldev.agregadordeinvestimentos.mappers.UserMapper;
import com.sinvaldev.agregadordeinvestimentos.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<ResponseUserDto> creatUser(@RequestBody RequestUserDto requestUserDto) {
        UserDto userDto = userService.createUser(requestUserDto);

        return ResponseEntity
                .created(URI.create("/v1/user/" + userMapper
                .userDtoToResponseUserDto(userDto)
                .userId()))
                .build();
    }
}
