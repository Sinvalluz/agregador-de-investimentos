package com.sinvaldev.agregadordeinvestimentos.service;

import com.sinvaldev.agregadordeinvestimentos.dtos.RequestUserDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.UserDto;
import com.sinvaldev.agregadordeinvestimentos.exception.EmailAlreadyExistsException;
import com.sinvaldev.agregadordeinvestimentos.mappers.UserMapper;
import com.sinvaldev.agregadordeinvestimentos.model.User;
import com.sinvaldev.agregadordeinvestimentos.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            throw new EmailAlreadyExistsException("Email já cadastrado");
        }

        User user = userRepository.save(userMapper.requestUserDtoToUser(requestUserDto));
        log.info("Usuário criado com sucesso");
        return userMapper.userToUserDto(user);
    }
}
