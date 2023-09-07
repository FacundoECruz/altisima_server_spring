package com.facu.altisima.service;

import com.facu.altisima.dao.api.UserRepository;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.UserServiceAPI;
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
    // Pensar como testear los casos negativos o que den error tambien.
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

        List<User> returnedUsersList = userService.getAll();

        assertEquals(users, returnedUsersList);
    }

    @Test
    public void successfulUserCreate() {
        //Testear si el usuario ya existe
        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.save(user);

        assertEquals(createdUser, user);
    }

    @Test
    public void successfulGetUserById() {
        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findById(user.getId())).thenReturn(optionalUser);
        User retrievedUser = userService.get(user.getId());

        assertEquals(retrievedUser, user);
    }

    @Test
    public void successfulDeleteUser() {

        doNothing().when(userRepository).deleteById(user.getId());

        userService.delete(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void successfulEditUser() {
        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findById(user.getId())).thenReturn(optionalUser);

        User userChanges = new User("1", "Jorge", "www.image.com/jorge", "jorge", 0);

        when(userRepository.save(userChanges)).thenReturn(userChanges);

        User retrievedUser = userService.put(user.getId(), userChanges);

        assertEquals(userChanges, retrievedUser);
    }
}
