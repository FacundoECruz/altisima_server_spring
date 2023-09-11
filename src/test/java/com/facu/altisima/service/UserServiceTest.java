package com.facu.altisima.service;

import com.facu.altisima.controller.dto.LoginRequest;
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

    @Test
    public void returnAllUsers() {
        List<User> users = new ArrayList<>();

        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        ServiceResult<List<User>> returnedUsersList = userService.getAll();

        assertEquals(users, returnedUsersList.getData());
    }

    @Test
    public void testDatabaseConnectionUserListError() {
        when(userRepository.findAll()).thenReturn(null);
        String expectedMsg = "No se encontraron usuarios.";

        ServiceResult<List<User>> result = userService.getAll();

        assertEquals(expectedMsg, result.getErrorMessage());
    }

    @Test
    public void successfulUserCreate() {
        when(userRepository.save(user)).thenReturn(user);
        ServiceResult<User> createdUser = userService.save(user);

        assertEquals(createdUser.getData(), user);
    }

    @Test
    public void usernameAlreadyExists() {
        when(userRepository.save(user)).thenReturn(null);
        String expectedMsg = "El nombre de usuario ya existe";

        ServiceResult<User> createdUser = userService.save(user);

        assertEquals(expectedMsg, createdUser.getErrorMessage());

    }

    @Test
    public void successfulGetUserById() {
        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findById(user.getId())).thenReturn(optionalUser);
        ServiceResult<User> retrievedUser = userService.get(user.getId());

        assertEquals(retrievedUser.getData(), user);
    }

    @Test
    public void userDoesNotExists() {
        String expectedMsg = "El nombre de usuario no existe";

        when(userRepository.findById(user.getId())).thenReturn((null));
        ServiceResult<User> retrievedUser = userService.get(user.getId());

        assertEquals(retrievedUser.getErrorMessage(), expectedMsg);
    }

    @Test
    public void successfulDeleteUser() {

        doNothing().when(userRepository).deleteById(user.getId());

        userService.delete(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    public void unsuccessfulDeleteUser() {

    }

    @Test
    public void successfulEditUser() {
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(user.getId())).thenReturn(optionalUser);

        User userChanges = new User("1", "Jorge", "www.image.com/jorge", "jorge", 0);
        when(userRepository.save(userChanges)).thenReturn(userChanges);

        ServiceResult<User> retrievedUser = userService.put(user.getId(), userChanges);
        assertEquals(userChanges, retrievedUser.getData());
    }

    @Test
    public void unsuccessfulEditUser() {
        String expectedMsg = "El nombre de usuario no existe";
        when(userRepository.findById(user.getId())).thenReturn(null);

        User userChanges = new User("1", "Jorge", "www.image.com/jorge", "jorge", 0);
        ServiceResult<User> retrievedUser = userService.put(user.getId(), userChanges);

        assertEquals(retrievedUser.getErrorMessage(), expectedMsg);
    }

    @Test
    public void successfulLogin() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        LoginRequest loginRequest = new LoginRequest("Facu", "facu");
        ServiceResult<User> retrievedUser = userService.login(loginRequest);

        assertEquals(retrievedUser.getData(), user);
    }

    @Test
    public void unsuccessfulLogin() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        String expectedMsg = "No se encontraron usuarios.";
        LoginRequest loginRequest = new LoginRequest("Facu", "facu");

        ServiceResult<User> retrievedUser = userService.login(loginRequest);

        assertEquals(retrievedUser.getErrorMessage(), expectedMsg);
    }
}
