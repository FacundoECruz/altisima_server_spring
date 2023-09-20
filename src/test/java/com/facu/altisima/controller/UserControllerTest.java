package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.LoginRequestDto;
import com.facu.altisima.model.User;
import com.facu.altisima.service.impl.UserServiceImpl;
import com.facu.altisima.service.utils.ServiceResult;
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
    User user = new User("1", "Facu", "www.image.com/facu", "lapass", 0);
    String path = "/users";
    String userJson = objectMapper.writeValueAsString(user);
    ServiceResult<User> succeedUser = ServiceResult.success(user);

    public UserControllerTest() throws JsonProcessingException {
    }

    @Test
    public void findUserByUsername() throws Exception {
        when(userService.get(user.getUsername())).thenReturn(succeedUser);
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
        when(userService.get(userThatDoesNotExist)).thenReturn(unsuccessfulUser);
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
        ServiceResult<User> receivedUserFromService = ServiceResult.error(expectedErrMsg);
        when(userService.save(user)).thenReturn(receivedUserFromService);


        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isConflict())
                .andExpect(content().string(expectedErrMsg));
    }

    @Test
    public void successfulLogin() throws Exception {
        LoginRequestDto loginRequestDto = new LoginRequestDto("facu", "facu");
        String loginRequestJson = objectMapper.writeValueAsString(loginRequestDto);
        when(userService.login(loginRequestDto)).thenReturn(succeedUser);

        mockMvc.perform(post(path + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(userJson));
    }

    @Test
    public void loginUserDoesNotExist() throws Exception {
        String expected = "No se encontraron usuarios";
        when(userService.login(any(LoginRequestDto.class)))
                .thenReturn(ServiceResult.error(expected));
        String loginRequestJson = serializeLoginRequest("facu", "facu");

        mockMvc.perform(post(path + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expected));
    }

    @Test
    public void invalidPassword() throws Exception {
        String expectedErrMsg = "Usuario o contraseña invalidos";
        LoginRequestDto loginRequestDto = new LoginRequestDto("facu", "facu");
        ServiceResult<User> receivedUserFromService = ServiceResult.error(expectedErrMsg);
        String loginRequestJson = objectMapper.writeValueAsString(loginRequestDto);
        when(userService.login(loginRequestDto)).thenReturn(receivedUserFromService);

        mockMvc.perform(post(path + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(expectedErrMsg));
    }

    private String serializeLoginRequest(String username, String password) throws JsonProcessingException {
        LoginRequestDto loginRequestDto = new LoginRequestDto(username, password);
        return objectMapper.writeValueAsString(loginRequestDto);
    }

    @Test
    public void successfulUserEdit() throws Exception {
        ServiceResult<User> changedUser = ServiceResult.success(user);
        when(userService.put(user.getUsername(), user)).thenReturn(changedUser);
        String urlTemplate = path + "/" + user.getUsername();

        mockMvc.perform(put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(userJson));
    }

    @Test
    public void unsuccessfulUserEdit() throws Exception {
        String expectedErrMsg = "El nombre de usuario no existe";
        ServiceResult<User> changedUser = ServiceResult.error(expectedErrMsg);
        String userThatDoesNotExist = "IAmNot";
        when(userService.put(userThatDoesNotExist, user)).thenReturn(changedUser);
        String urlTemplate = path + "/" + userThatDoesNotExist;

        mockMvc.perform(put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedErrMsg));
    }

    @Test
    public void successfulUserDelete() throws Exception {
        doNothing().when(userService).delete(user.getId());
        when(userService.get(user.getUsername())).thenReturn(succeedUser);
        String urlTemplate = path + "/" + user.getUsername();

        mockMvc.perform(delete(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void userToDeleteDoesNotExist() throws Exception {
        String expected = "No se encontro el usuario";
        doNothing().when(userService).delete(user.getId());
        ServiceResult<User> serviceUser = ServiceResult.error(expected);
        when(userService.get(user.getUsername())).thenReturn(serviceUser);
        String urlTemplate = path + "/" + user.getUsername();

        mockMvc.perform(delete(urlTemplate))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expected));
    }
}
