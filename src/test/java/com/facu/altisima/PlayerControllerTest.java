package com.facu.altisima;

import com.facu.altisima.model.Player;
import com.facu.altisima.service.impl.PlayerServiceImpl;
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

    @Test
    public void testPlayersEndpoint() throws Exception {
        List<Player> players = new ArrayList<>();

        when(playerService.getAll()).thenReturn(players);

        mockMvc.perform(get("/players/api/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[]"));

    }

    @Test
    public void testSavePlayerEndpoint() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Player player = new Player("1", "Facu", "www.image.com/facu", 0, 0, 0);

        when(playerService.save(player)).thenReturn(player);

        mockMvc.perform(post("/players/api/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testFindByIdEndpoint() throws Exception {

        Player player = new Player("1", "Facu", "www.image.com/facu", 0, 0, 0);
        when(playerService.get("1")).thenReturn(player);

        mockMvc.perform(get("/players/api/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeleteEndpoint() throws Exception {
        doNothing().when(playerService).delete("1");

        mockMvc.perform(delete("/players/api/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }
}
