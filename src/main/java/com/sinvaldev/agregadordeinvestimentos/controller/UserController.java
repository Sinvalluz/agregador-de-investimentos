package com.sinvaldev.agregadordeinvestimentos.controller;

import com.sinvaldev.agregadordeinvestimentos.dtos.RequestUserDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.ResponseUserDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.UserDto;
import com.sinvaldev.agregadordeinvestimentos.mappers.UserMapper;
import com.sinvaldev.agregadordeinvestimentos.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<ResponseUserDto> creatUser(@RequestBody RequestUserDto requestUserDto) {
        UserDto userDto = userService.createUser(requestUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userDtoToResponseUserDto(userDto));
    }
}
