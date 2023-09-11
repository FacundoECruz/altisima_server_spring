package com.facu.altisima.controller;

import com.facu.altisima.model.Player;
import com.facu.altisima.model.User;
import com.facu.altisima.service.impl.PlayerServiceImpl;
import com.facu.altisima.service.utils.ServiceResult;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PlayerServiceImpl playerService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void returnAllPlayers() throws Exception {
        ServiceResult<List<Player>> players = ServiceResult.success(new ArrayList<>()) ;

        when(playerService.getAll()).thenReturn(players);

        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[]"));

    }

    @Test
    public void successfulSavePlayer() throws Exception {

        Player player = new Player("1", "Facu", "www.image.com/facu", 0, 0, 0);

        when(playerService.save(player)).thenReturn(player);

        String playerJson = objectMapper.writeValueAsString(player);

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(playerJson));
    }

    @Test
    public void findPlayerById() throws Exception {

        Player player = new Player("1", "Facu", "www.image.com/facu", 0, 0, 0);
        when(playerService.get("1")).thenReturn(player);

        String playerJson = objectMapper.writeValueAsString(player);
        mockMvc.perform(get("/players/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(playerJson));
    }

    @Test
    public void successfulPlayerDelete() throws Exception {
        doNothing().when(playerService).delete("1");

        mockMvc.perform(delete("/players/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void successfulPlayerEdit() throws Exception{

        Player playerChanges = new Player("1", "messi", "www.image.com/messi", 0, 0, 0);

        when(playerService.put("1", playerChanges)).thenReturn(playerChanges);

        String playerJson = objectMapper.writeValueAsString(playerChanges);
        mockMvc.perform(put("/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerJson))
                .andExpect(status().isOk())
                .andExpect(content().string(playerJson));
    }
}
