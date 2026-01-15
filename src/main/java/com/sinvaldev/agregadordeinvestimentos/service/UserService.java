package com.sinvaldev.agregadordeinvestimentos.service;

import com.sinvaldev.agregadordeinvestimentos.dtos.RequestUserDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.UserDto;
import com.sinvaldev.agregadordeinvestimentos.exception.EmailAlreadyExistsException;
import com.sinvaldev.agregadordeinvestimentos.exception.UserNotFoundException;
import com.sinvaldev.agregadordeinvestimentos.mappers.UserMapper;
import com.sinvaldev.agregadordeinvestimentos.model.User;
import com.sinvaldev.agregadordeinvestimentos.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto createUser(RequestUserDto requestUserDto) {
        if (userRepository.existsUserByEmail(requestUserDto.email())) {
            throw new EmailAlreadyExistsException();
        }

        User user = userRepository.save(userMapper.requestUserDtoToUser(requestUserDto));
        log.info("Usuário criado com sucesso");
        return userMapper.userToUserDto(user);
    }

    public UserDto findUserById (String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(UserNotFoundException::new);

        return userMapper.userToUserDto(user);
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(userMapper::userToUserDto).toList();
    }
}
