package com.sinvaldev.agregadordeinvestimentos.service;

import com.sinvaldev.agregadordeinvestimentos.dtos.account.RequestAccountDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.account.ResponseAccountDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.user.RequestUserDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.user.UserDto;
import com.sinvaldev.agregadordeinvestimentos.exception.EmailAlreadyExistsException;
import com.sinvaldev.agregadordeinvestimentos.exception.UserNotFoundException;
import com.sinvaldev.agregadordeinvestimentos.mappers.AccountMapper;
import com.sinvaldev.agregadordeinvestimentos.mappers.UserMapper;
import com.sinvaldev.agregadordeinvestimentos.model.Account;
import com.sinvaldev.agregadordeinvestimentos.model.BillingAddress;
import com.sinvaldev.agregadordeinvestimentos.model.User;
import com.sinvaldev.agregadordeinvestimentos.repository.AccountRepository;
import com.sinvaldev.agregadordeinvestimentos.repository.BillingAddressRepository;
import com.sinvaldev.agregadordeinvestimentos.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final BillingAddressRepository billingAddressRepository;

    public UserDto createUser(RequestUserDto requestUserDto) {
        if (userRepository.existsUserByEmail(requestUserDto.email())) throw new EmailAlreadyExistsException();

        User user = userRepository.save(userMapper.requestUserDtoToUser(requestUserDto));
        log.info("Usuário criado com sucesso");

        return userMapper.userToUserDto(user);
    }

    public UserDto findUserById (String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(UserNotFoundException::new);

        log.info("Usuário encontrado");

        return userMapper.userToUserDto(user);
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(userMapper::userToUserDto).toList();
    }

    public void updateUserById(String userId, RequestUserDto requestUserDto) {
        UUID uuid = UUID.fromString(userId);
        User user = userRepository.findById(uuid).orElseThrow(UserNotFoundException::new);

        if (userRepository.existsByEmailAndUserIdNot(requestUserDto.email(), uuid)) {
            throw new EmailAlreadyExistsException();
        }

        user.setUserName(requestUserDto.userName() == null || requestUserDto.userName().isBlank() ? user.getUserName(): requestUserDto.userName());
        user.setEmail(requestUserDto.email() == null || requestUserDto.email().isBlank() ? user.getEmail(): requestUserDto.email());
        user.setPassword(requestUserDto.password() == null || requestUserDto.password().isBlank() ? user.getPassword(): requestUserDto.password());

        userRepository.save(user);

        log.info("Usuário atualizado com sucesso");
    }

    public void deleteById(String userId) {
        UUID uuid = UUID.fromString(userId);

        if (userRepository.existsById(uuid)) {
            userRepository.deleteById(uuid);
            log.info("Usuário deletado com sucesso");
        } else {
            throw new UserNotFoundException();
        }
    }

    public void createAccount(String userId, RequestAccountDto requestAccountDto) {
        UUID uuid = UUID.fromString(userId);
        User user = userRepository.findById(uuid).orElseThrow(UserNotFoundException::new);

        Account account = new Account(
                null,
                requestAccountDto.description(),
                user,
                null,
                new ArrayList<>());

        log.info("Conta mapeada: {}", account.toString());

        var accountCreated = accountRepository.save(account);

        BillingAddress billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                accountCreated,
                requestAccountDto.street(),
                requestAccountDto.number());

        billingAddressRepository.save(billingAddress);
    }

    public List<ResponseAccountDto> listAccounts(String userId) {
        UUID uuid = UUID.fromString(userId);
        User user = userRepository.findById(uuid).orElseThrow(UserNotFoundException::new);

        return user
                .getAccounts()
                .stream()
                .map(accountMapper::accountToResponseAccountDto)
                .toList();
    }
}
