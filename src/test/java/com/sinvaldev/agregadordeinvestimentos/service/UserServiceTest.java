package com.sinvaldev.agregadordeinvestimentos.service;

import com.sinvaldev.agregadordeinvestimentos.dtos.RequestUserDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.UserDto;
import com.sinvaldev.agregadordeinvestimentos.exception.EmailAlreadyExistsException;
import com.sinvaldev.agregadordeinvestimentos.exception.UserNotFoundException;
import com.sinvaldev.agregadordeinvestimentos.mappers.UserMapper;
import com.sinvaldev.agregadordeinvestimentos.model.User;
import com.sinvaldev.agregadordeinvestimentos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Arrange 👉 O que eu preciso preparar?
// Act 👉 O que eu vou executar?
// Assert 👉 O que eu espero que aconteça?

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("should create a user with successfully")
        void shouldCreateAUserWithSuccess() {
            // Arrange

            RequestUserDto requestUserDto = new RequestUserDto("userName", "email@email.com", "password");
            User user = new User(UUID.randomUUID(), "userName", "email@email.com", "password", Instant.now(), Instant.now());
            UserDto userDto = new UserDto(user.getUserId(), user.getUserName(), user.getEmail(), user.getCreationTimestamp(), user.getUpdateTimestamp());

            when(userRepository.existsUserByEmail(requestUserDto.email())).thenReturn(false);
            when(userMapper.requestUserDtoToUser(requestUserDto)).thenReturn(user);
            when(userRepository.save(userArgumentCaptor.capture())).thenReturn(user);
            when(userMapper.userToUserDto(user)).thenReturn(userDto);

            // Act
            UserDto output = userService.createUser(requestUserDto);

            // Assert
            assertNotNull(output);
            User userCapture = userArgumentCaptor.getValue();
            assertEquals(requestUserDto.userName(), userCapture.getUserName());
            assertEquals(requestUserDto.email(), userCapture.getEmail());
            assertEquals(requestUserDto.password(), userCapture.getPassword());

        }

        @Test
        @DisplayName("should return an exception if email already exists")
        void shouldReturnAnExceptionIfEmailAlreadyExists() {
            // Arrange
            RequestUserDto requestUserDto = new RequestUserDto("userName", "email@email.com", "password");

            when(userRepository.existsUserByEmail(requestUserDto.email())).thenReturn(true);

            // Act + Assert
            assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(requestUserDto));

            verify(userMapper, never()).requestUserDtoToUser(requestUserDto);
            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    class findUserById {

        @Test
        @DisplayName("Should find user by id with success when optional is present")
        void shouldFindUserByIdWithSuccessWhenOptionalIsPresent() {
            // Arrange
            User user = new User(
                    UUID.randomUUID(),
                    "userName",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    Instant.now());

            UserDto userDto = new UserDto(
                    user.getUserId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getCreationTimestamp(),
                    user.getUpdateTimestamp());

            when(userRepository.findById(uuidArgumentCaptor.capture())).thenReturn(Optional.of(user));
            when(userMapper.userToUserDto(user)).thenReturn(userDto);


            // Act
            var output = userService.findUserById(user.getUserId().toString());

            // Assert
            assertNotNull(output);
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should throw an exception when user is not found")
        void shouldThrowAnExceptionWhenUserIsNotFound() {
            // Arrange
            String uuid = UUID.randomUUID().toString();
            when(userRepository.findById(uuidArgumentCaptor.capture())).thenReturn(Optional.empty());

            // Act + Assert
            assertThrows(UserNotFoundException.class, () -> userService.findUserById(uuid));
            verify(userMapper, never()).userToUserDto(any());
            assertEquals(uuid, uuidArgumentCaptor.getValue().toString());
        }
    }

    @Nested
    class findAllUsers {
        @Test
        @DisplayName("Should return all users with success")
        void ShouldReturnAllUsersWithSuccess() {

            // Arrange

            User user = new User(
                    UUID.randomUUID(),
                    "userName",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    Instant.now());

            UserDto userDto = new UserDto(
                    user.getUserId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getCreationTimestamp(),
                    user.getUpdateTimestamp());

            List<User> userList = List.of(user);

            when(userRepository.findAll()).thenReturn(userList);
            when(userMapper.userToUserDto(user)).thenReturn(userDto);
            // Act

            List<UserDto> output = userService.findAllUsers();

            // Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());

        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should delete user with success")
        void shouldDeleteUserWithSuccess() {

            // Arrange
            String uuid = UUID.randomUUID().toString();

            when(userRepository.existsById(uuidArgumentCaptor.capture())).thenReturn(true);
            doNothing().when(userRepository).deleteById(uuidArgumentCaptor.capture());

            // Act
            userService.deleteById(uuid);

            // Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertNotNull(idList);
            assertEquals(uuid, idList.getFirst().toString());
            assertEquals(uuid, idList.get(1).toString());
        }

        @Test
        @DisplayName("Should throw exception if user is not found")
        void shouldThrowExceptionIfUserIsNotFound() {
            // Arrange
            String uuid = UUID.randomUUID().toString();

            when(userRepository.existsById(uuidArgumentCaptor.capture())).thenReturn(false);

            // Act + assert
            assertThrows(UserNotFoundException.class, () -> userService.deleteById(uuid));

            verify(userRepository, never()).deleteById(uuidArgumentCaptor.getValue());
            assertEquals(uuid, uuidArgumentCaptor.getValue().toString());

        }
    }

    @Nested
    class updateUserById {

        @Test
        @DisplayName("should update user with success")
        void shouldUpdateUserWithSuccess() {
            // Arrange 👉 O que eu preciso preparar?
            RequestUserDto requestUserDto = new RequestUserDto(
                    "newUserName",
                    "email@email.com",
                    "newPassword");

            User user = new User(
                    UUID.randomUUID(),
                    "userName",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    Instant.now());

            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            doReturn(false).when(userRepository).existsByEmailAndUserIdNot(eq(requestUserDto.email()) ,uuidArgumentCaptor.capture());

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());


            // Act
            userService.updateUserById(user.getUserId().toString(), requestUserDto);
            // Assert

            var uuidList = uuidArgumentCaptor.getAllValues();
            assertNotNull(uuidList);
            assertNotNull(requestUserDto.email());
            assertNotNull(requestUserDto.userName());
            assertNotNull(requestUserDto.password());
            assertEquals(requestUserDto.userName(), userArgumentCaptor.getValue().getUserName());
            assertEquals(requestUserDto.email(), userArgumentCaptor.getValue().getEmail());
            assertEquals(requestUserDto.password(), userArgumentCaptor.getValue().getPassword());

        }

        @Test
        @DisplayName("should throw exception if user is not found")
        void shouldThrowExceptionIfUserIsNotFound() {
            // Arrange
            UUID uuid = UUID.randomUUID();

            RequestUserDto requestUserDto = new RequestUserDto(
                    "newUserName",
                    "email@email.com",
                    "newPassword");

            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            // Act + assert
            assertThrows(UserNotFoundException.class, () -> userService.updateUserById(uuid.toString(), requestUserDto));

            assertNotNull(uuidArgumentCaptor.getValue());
            assertEquals(uuid, uuidArgumentCaptor.getValue());
            verify(userRepository, never()).existsByEmailAndUserIdNot(any(), any());
            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("should throw exception if email already exists")
        void shouldThrowExceptionIfEmailAlreadyExists() {
            // Arrange
            User user = new User(
                    UUID.randomUUID(),
                    "userName",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    Instant.now());

            RequestUserDto requestUserDto = new RequestUserDto(
                    "newUserName",
                    "email@email.com",
                    "newPassword");

            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());
            doReturn(true).when(userRepository).existsByEmailAndUserIdNot(eq(requestUserDto.email()), uuidArgumentCaptor.capture());

            // Act + Assert
            assertThrows(EmailAlreadyExistsException.class, () -> userService.updateUserById(user.getUserId().toString(), requestUserDto));
            assertEquals(user.getUserId(), uuidArgumentCaptor.getAllValues().getFirst());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getAllValues().getLast());

        }
    }
}