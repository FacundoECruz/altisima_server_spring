package com.facu.altisima.service;

import com.facu.altisima.controller.dto.LoginRequestDto;
import com.facu.altisima.controller.dto.legacyDtos.EditUserDto;
import com.facu.altisima.dao.api.UserRepository;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.UserServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserServiceAPI userService;

    @MockBean
    UserRepository userRepository;

    User user = new User("Facu", "facu@gmail.com", "www.image.com/facu", "lapass", 0);
    Optional<User> optionalUser = Optional.of(user);
    Optional<User> emptyOptional = Optional.empty();
    List<User> users = new ArrayList<>();

    @Test
    public void returnAllUsers() {
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        ServiceResult<List<User>> returnedUsersList = userService.getAll();

        assertEquals(users, returnedUsersList.getData());
    }

    @Test
    public void testDatabaseConnectionUserListError() {
        when(userRepository.findAll()).thenReturn(users);
        String expectedMsg = "No se encontraron usuarios";

        ServiceResult<List<User>> result = userService.getAll();

        assertEquals(expectedMsg, result.getErrorMessage());
    }

    @Test
    public void successfulUserCreate() {
        when (userRepository.save(user)).thenReturn(user);

        ServiceResult<User> createdUser = userService.save(user);

        assertEquals(createdUser.getData(), user);
    }

    @Test
    public void usernameToCreateAlreadyExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(optionalUser);
        String expectedMsg = "El nombre de usuario ya existe";

        ServiceResult<User> createdUser = userService.save(user);

        assertEquals(expectedMsg, createdUser.getErrorMessage());
    }

    @Test
    public void successfulGetUserByUsername() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(optionalUser);

        ServiceResult<User> retrievedUser = userService.get(user.getUsername());

        assertEquals(retrievedUser.getData(), user);
    }

    @Test
    public void userToGetDoesNotExists() {
        String expectedMsg = "El nombre de usuario no existe";
        when(userRepository.findById(user.getUsername())).thenReturn((emptyOptional));

        ServiceResult<User> retrievedUser = userService.get(user.getUsername());

        assertEquals(retrievedUser.getErrorMessage(), expectedMsg);
    }

    @Test
    public void successfulDeleteUser() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(optionalUser);

        userService.delete(user.getUsername());

        verify(userRepository, times(1)).deleteByUsername(user.getUsername());
    }

    @Test
    public void unsuccessfulDeleteUser() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(emptyOptional);

        userService.delete(user.getUsername());

        verify(userRepository, times(0)).deleteByUsername(user.getUsername());
    }

    @Test
    public void successfulEditedUser() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(optionalUser);
        EditUserDto userChanges = new EditUserDto("newPasswords", "newImage");
        when(userRepository.save(any(User.class))).thenReturn(user);

        ServiceResult<User> retrievedUser = userService.put(user.getUsername(), userChanges);

        assertEquals(userChanges.getPassword(), retrievedUser.getData().getPassword());
        assertEquals(userChanges.getImage(), retrievedUser.getData().getImage());
    }

    @Test
    public void userToEditDoesNotExists() {
        String expectedMsg = "El nombre de usuario no existe";
        when(userRepository.findByUsername(user.getUsername())).thenReturn(emptyOptional);
        EditUserDto userChanges = new EditUserDto("newPasword", "newImage");

        ServiceResult<User> retrievedUser = userService.put(user.getUsername(), userChanges);

        assertEquals(retrievedUser.getErrorMessage(), expectedMsg);
    }

    @Test
    public void successfulLogin() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("Facu", "lapass");
        when(userRepository.findByUsername(loginRequestDto.getUsername())).thenReturn(optionalUser);

        ServiceResult<User> retrievedUser = userService.login(loginRequestDto);

        assertEquals(retrievedUser.getData(), user);
    }

    @Test
    public void userToLoginDoesNotExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(emptyOptional);
        String expectedMsg = "No se encontraron usuarios";
        LoginRequestDto loginRequestDto = new LoginRequestDto("Facu", "facu");

        ServiceResult<User> retrievedUser = userService.login(loginRequestDto);

        assertEquals(retrievedUser.getErrorMessage(), expectedMsg);
    }

    @Test
    public void invalidPasswordInLogin() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(optionalUser);
        String expectedMsg = "Usuario o contraseña invalidos";
        LoginRequestDto loginRequestDto = new LoginRequestDto("Facu", "asdf");

        ServiceResult<User> retrievedUser = userService.login(loginRequestDto);

        assertEquals(retrievedUser.getErrorMessage(), expectedMsg);

    }
}
