package com.facu.altisima.service;

import com.facu.altisima.controller.dto.LoginRequestDto;
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

    User user = new User("1", "Facu", "www.image.com/facu", "lapass", 0);
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
        when(userRepository.findById(user.getId())).thenReturn((emptyOptional));

        ServiceResult<User> retrievedUser = userService.get(user.getId());

        assertEquals(retrievedUser.getErrorMessage(), expectedMsg);
    }

    @Test
    public void successfulDeleteUser() {
        when(userRepository.findById(user.getId())).thenReturn(optionalUser);

        userService.delete(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void unsuccessfulDeleteUser() {
        when(userRepository.findById(user.getId())).thenReturn(emptyOptional);

        userService.delete(user.getId());

        verify(userRepository, times(0)).deleteById(user.getId());
    }

    @Test
    public void successfulEditedUser() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(optionalUser);
        User userChanges = new User("1", "Jorge", "www.image.com/jorge", "jorge", 0);
        when(userRepository.save(userChanges)).thenReturn(userChanges);

        ServiceResult<User> retrievedUser = userService.put(user.getUsername(), userChanges);

        assertEquals(userChanges, retrievedUser.getData());
    }

    @Test
    public void userToEditDoesNotExists() {
        String expectedMsg = "El nombre de usuario no existe";
        when(userRepository.findByUsername(user.getUsername())).thenReturn(emptyOptional);
        User userChanges = new User("1", "Jorge", "www.image.com/jorge", "jorge", 0);

        ServiceResult<User> retrievedUser = userService.put(user.getId(), userChanges);

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
