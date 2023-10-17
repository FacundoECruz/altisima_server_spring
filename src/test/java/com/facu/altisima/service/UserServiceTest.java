package com.facu.altisima.service;

import com.facu.altisima.controller.dto.LoginRequestDto;
import com.facu.altisima.controller.dto.legacyDtos.CreateUserDto;
import com.facu.altisima.controller.dto.legacyDtos.EditUserDto;
import com.facu.altisima.model.Player;
import com.facu.altisima.repository.PlayerRepository;
import com.facu.altisima.repository.UserRepository;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.UserServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import com.facu.altisima.utils.FixedIdGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
    @MockBean
    PlayerRepository playerRepository;
    FixedIdGenerator idGenerator = new FixedIdGenerator("someFakeId");
    User user = new User(idGenerator.generate(), "Facu", "facu@facu", "www.image.com/facu", "asdfg",0);
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
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(playerRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        ServiceResult<User> createdUser = userService.save(user);

        assertEquals(user, createdUser.getData());
    }

    @Test
    public void usernameToCreateAlreadyExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        String expectedMsg = "El nombre de usuario ya existe";

        ServiceResult<User> createdUser = userService.save(user);

        assertEquals(expectedMsg, createdUser.getErrorMessage());
    }

    @Test
    public void successfulGetUserByUsername() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        ServiceResult<User> retrievedUser = userService.get(user.getUsername());

        assertEquals(retrievedUser.getData(), user);
    }

    @Test
    public void userToGetDoesNotExists() {
        String expectedMsg = "El nombre de usuario no existe";
        when(userRepository.findById(user.getUsername())).thenReturn((Optional.empty()));

        ServiceResult<User> retrievedUser = userService.get(user.getUsername());

        assertEquals(retrievedUser.getErrorMessage(), expectedMsg);
    }

    @Test
    public void successfulDeleteUser() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        userService.delete(user.getUsername());

        verify(userRepository, times(1)).deleteByUsername(user.getUsername());
    }

    @Test
    public void unsuccessfulDeleteUser() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        userService.delete(user.getUsername());

        verify(userRepository, times(0)).deleteByUsername(user.getUsername());
    }

    @Test
    public void successfulEditedUser() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        EditUserDto userChanges = new EditUserDto("newPassword", "newImage");
        when(userRepository.save(any(User.class))).thenReturn(user);

        ServiceResult<User> retrievedUser = userService.put(userChanges.toDomain(user.getUsername()));

        assertEquals(userChanges.getPassword(), retrievedUser.getData().getPassword());
        assertEquals(userChanges.getImage(), retrievedUser.getData().getImage());
    }

    @Test
    public void userToEditDoesNotExists() {
        String expectedMsg = "El nombre de usuario no existe";
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        EditUserDto userChanges = new EditUserDto("newPasword", "newImage");

        ServiceResult<User> retrievedUser = userService.put(userChanges.toDomain(user.getUsername()));

        assertEquals(retrievedUser.getErrorMessage(), expectedMsg);
    }

    @Test
    public void successfulLogin() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(user.getUsername(), user.getPassword());
        when(userRepository.findByUsername(loginRequestDto.getUsername())).thenReturn(Optional.of(user));

        ServiceResult<User> retrievedUser = userService.login(loginRequestDto.toDomain());

        assertEquals(user, retrievedUser.getData());
    }

    @Test
    public void userToLoginDoesNotExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        String expectedMsg = "Usuario no encontrado";
        LoginRequestDto loginRequestDto = new LoginRequestDto("Facu", "facu");

        ServiceResult<User> retrievedUser = userService.login(loginRequestDto.toDomain());

        assertEquals(expectedMsg, retrievedUser.getErrorMessage());
    }

    // ESTA PARTE DEL TEST DE LOGIN SE PUEDE EXTENDER
    @Test
    public void invalidPasswordInLogin() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        String expectedMsg = "Usuario o contraseña inválidos";
        LoginRequestDto loginRequestDto = new LoginRequestDto("Facu", "asdf");

        ServiceResult<User> retrievedUser = userService.login(loginRequestDto.toDomain());

        assertEquals(retrievedUser.getErrorMessage(), expectedMsg);

    }

    @Test
    public void successfulAssociateUser(){
        CreateUserDto createUserDto = new CreateUserDto("newUser", "newUser@gmail.com", "password", "www.image.com/newUser");
        Player preExistingPlayer = new Player(idGenerator.generate(), "newUser", "www.image.com/default", 2, 15, 435);
        when(playerRepository.findByUsername(createUserDto.getUsername())).thenReturn(Optional.of(preExistingPlayer));
        preExistingPlayer.setImage(createUserDto.getImage());

        ServiceResult<User> userResult = userService.associate(createUserDto.toDomain());

        verify(playerRepository, times(1)).save(preExistingPlayer);
        assertEquals(userResult.getData().getImage(), preExistingPlayer.getImage());
    }
}
