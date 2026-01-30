package com.sinvaldev.agregadordeinvestimentos.controller;


import com.sinvaldev.agregadordeinvestimentos.dtos.account.RequestAccountDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.account.ResponseAccountDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.user.RequestUserDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.user.ResponseUserDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.user.UserDto;
import com.sinvaldev.agregadordeinvestimentos.mappers.UserMapper;
import com.sinvaldev.agregadordeinvestimentos.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


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

        return ResponseEntity
                .created(URI.create("/v1/user/" + userMapper
                .userDtoToResponseUserDto(userDto)
                .userId()))
                .build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseUserDto> getUserById (@PathVariable String userId) {

        UserDto userDto = userService.findUserById(userId);

        return ResponseEntity.ok(userMapper.userDtoToResponseUserDto(userDto));
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> getAllUsers () {
        List<UserDto> userDtoList = userService.findAllUsers();

        List<ResponseUserDto> responseUserDtoList = userDtoList.stream().map(userMapper::userDtoToResponseUserDto).toList();

        return ResponseEntity.ok(responseUserDtoList);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable String userId, @RequestBody RequestUserDto requestUserDto) {
        userService.updateUserById(userId, requestUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount(@PathVariable String userId,
                                           @RequestBody RequestAccountDto requestAccountDto) {
        userService.createAccount(userId, requestAccountDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<ResponseAccountDto>> createAccount(@PathVariable String userId) {
        List<ResponseAccountDto> account = userService.listAccounts(userId);
        return ResponseEntity.ok(account);
    }
}
