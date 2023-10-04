package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.LoginRequest;
import com.facu.altisima.controller.dto.LoginRequestDto;
import com.facu.altisima.controller.dto.legacyDtos.EditUserDto;
import com.facu.altisima.model.User;
import com.facu.altisima.service.impl.UserServiceImpl;
import com.facu.altisima.service.utils.ServiceResult;
import com.facu.altisima.utils.FixedIdGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserServiceImpl userService;
    ObjectMapper objectMapper = new ObjectMapper();
    FixedIdGenerator idGenerator = new FixedIdGenerator("someFakeId");
    User user = new User(idGenerator.generate(), "Facu", "facu@facu", "www.image.com/facu", "asdfg", 0);
    String path = "/users";
    String userJson = objectMapper.writeValueAsString(user);
    ServiceResult<User> succeedUser = ServiceResult.success(user);
    EditUserDto userChanges = new EditUserDto("qwerty", "www.image.com/otraImagen");
    String userChangesJson = objectMapper.writeValueAsString(userChanges);
    LoginRequestDto loginRequestDto = new LoginRequestDto("facu", "facundo");
    LoginRequest loginRequest = loginRequestDto.toDomain();
    public UserControllerTest() throws JsonProcessingException {
    }

    @Test
    public void findUserByUsername() throws Exception {
        when(userService.get(user.getUsername()))
                .thenReturn(succeedUser);
        String urlTemplate = path + "/" + user.getUsername();

        mockMvc.perform(get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(userJson));
    }

    @Test
    public void userDoesNotExist() throws Exception {
        String expectedErrMsg = "El nombre de usuario no existe";
        String userThatDoesNotExist = "IMNotRegisteredHere";
        ServiceResult<User> unsuccessfulUser = ServiceResult.error(expectedErrMsg);
        when(userService.get(userThatDoesNotExist))
                .thenReturn(unsuccessfulUser);
        String urlTemplate = path + "/" + userThatDoesNotExist;

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedErrMsg));
    }

    @Test
    public void returnAllUsers() throws Exception {
        ServiceResult<List<User>> users = ServiceResult.success(new ArrayList<>());
        when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[]"));
    }

    @Test
    public void unsuccessfulReturnAllUsers() throws Exception {
        String expectedErrMsg = "No se encontraron usuarios";
        ServiceResult<List<User>> users = ServiceResult.error(expectedErrMsg);
        when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get(path))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(expectedErrMsg));
    }

    @Test
    public void successfulCreateUser() throws Exception {
        when(userService.save(user)).thenReturn(succeedUser);

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().string(userJson));
    }

    @Test
    public void usernameAlreadyExists() throws Exception {
        String expectedErrMsg = "El nombre de usuario ya existe";
        ServiceResult<User> expectedServiceResult = ServiceResult.error(expectedErrMsg);
        when(userService.save(user)).thenReturn(expectedServiceResult);

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isConflict())
                .andExpect(content().string(expectedErrMsg));
    }

    @Test
    public void successfulLogin() throws Exception {
        String loginRequestDtoJson = objectMapper.writeValueAsString(loginRequestDto);
        when(userService.login(loginRequest)).thenReturn(succeedUser);

        mockMvc.perform(post(path + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestDtoJson))
                .andExpect(status().isOk())
                .andExpect(content().string(userJson));
    }

    @Test
    public void loginUserDoesNotExist() throws Exception {
        String expected = "Usuario no encontrado";
        when(userService.login(loginRequest))
                .thenReturn(ServiceResult.error(expected));
        String loginRequestDtoJson = objectMapper.writeValueAsString(loginRequestDto);

        mockMvc.perform(post(path + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestDtoJson))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(expected));
    }

    @Test
    public void invalidPassword() throws Exception {
        String expected = "Usuario o contraseña invalidos";
        ServiceResult<User> expectedServiceResult = ServiceResult.error(expected);
        String loginRequestJson = objectMapper.writeValueAsString(loginRequestDto);
        when(userService.login(loginRequest)).thenReturn(expectedServiceResult);

        mockMvc.perform(post(path + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(expected));
    }

    @Test
    public void successfulUserEdit() throws Exception {
        String userChangesJson = objectMapper.writeValueAsString(userChanges);
        String urlTemplate = path + "/" + user.getUsername();
        when(userService.put(userChanges.toDomain(user.getUsername()))).thenReturn(succeedUser);

        mockMvc.perform(put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userChangesJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(userJson));
    }

    @Test
    public void unsuccessfulUserEdit() throws Exception {
        String expectedMsg = "El nombre de usuario no existe";
        ServiceResult<User> expectedService = ServiceResult.error(expectedMsg);
        String userThatDoesNotExist = "IAmNot";
        when(userService.put(userChanges.toDomain(userThatDoesNotExist))).thenReturn(expectedService);
        String urlTemplate = path + "/" + userThatDoesNotExist;

        mockMvc.perform(put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userChangesJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedMsg));
    }

    @Test
    public void successfulUserDelete() throws Exception {
        doNothing().when(userService).delete(user.getUsername());
        when(userService.get(user.getUsername())).thenReturn(succeedUser);
        String urlTemplate = path + "/" + user.getUsername();

        mockMvc.perform(delete(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void userToDeleteDoesNotExist() throws Exception {
        String expected = "No se encontro el usuario";
        doNothing().when(userService).delete(user.getUsername());
        ServiceResult<User> serviceUser = ServiceResult.error(expected);
        when(userService.get(user.getUsername())).thenReturn(serviceUser);
        String urlTemplate = path + "/" + user.getUsername();

        mockMvc.perform(delete(urlTemplate))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expected));
    }
}
