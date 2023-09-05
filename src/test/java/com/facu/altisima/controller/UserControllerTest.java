package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.LoginRequest;
import com.facu.altisima.model.User;
import com.facu.altisima.service.impl.UserServiceImpl;
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

    @Test
    public void returnAllUsers() throws Exception {
        List<User> users = new ArrayList<>();

        when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get("/users/api/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[]"));
    }

    @Test
    public void successfulCreateUser() throws Exception {
        User user = new User("1", "Facu", "www.image.com/facu", "lapass", 0);

        when(userService.save(user)).thenReturn(user);

        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users/api/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(userJson));
    }

    @Test
    public void findUserByUsername() throws Exception {

        User user = new User("1", "Facu", "www.image.com/facu", "lapass", 0);
        when(userService.get("1")).thenReturn(user);

        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(get("/users/api/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(userJson));
    }

    @Test
    public void successfulUserDelete() throws Exception {
        doNothing().when(userService).delete("messi");

        mockMvc.perform(delete("/players/api/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void successfulUserEdit() throws Exception {

        User userChanges = new User("1", "messi", "www.image.com/messi", "lapass", 0);

        when(userService.put("1", userChanges)).thenReturn(userChanges);

        String userJson = objectMapper.writeValueAsString(userChanges);
        mockMvc.perform(put("/users/api/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(userJson));
    }

    @Test
    public void successfulLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("facu", "facu");
        User expectedUser = new User("3", "facu", "www.image.com/facu", "facu", 0);

        String loginRequestJson = objectMapper.writeValueAsString(loginRequest);
        String expectedUserJson = objectMapper.writeValueAsString(expectedUser);

        when(userService.login(loginRequest)).thenReturn(expectedUser);

        mockMvc.perform(post("/users/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedUserJson));
    }
}
