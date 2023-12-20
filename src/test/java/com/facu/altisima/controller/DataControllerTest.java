package com.facu.altisima.controller;

import com.facu.altisima.model.PlayerData;
import com.facu.altisima.service.impl.DataService;
import com.facu.altisima.service.utils.ServiceResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DataControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    DataService dataService;
    ObjectMapper objectMapper = new ObjectMapper();
    String path = "/data";
    PlayerData playerData = new PlayerData("fakeId","Messi", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    @Test
    public void should_return_all_players_data() throws Exception {
        List<PlayerData> allPlayersData = new ArrayList<>();
        allPlayersData.add(playerData);
        ServiceResult<List<PlayerData>> response = ServiceResult.success(allPlayersData);
        when(dataService.getAll()).thenReturn(response);
        String resJson = objectMapper.writeValueAsString(response.getData());
        mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(content().string(resJson));
    }

    @Test
    public void should_return_unsuccessful_get_all_players() throws Exception {
        String expectedMsg = "players data could not be recovered";
        ServiceResult<List<PlayerData>> expectedRes = ServiceResult.error(expectedMsg);
        when(dataService.getAll()).thenReturn(expectedRes);
        mockMvc.perform(get(path))
                .andExpect(status().isConflict())
                .andExpect(content().string(expectedRes.getErrorMessage()));
    }

    @Test
    public void should_return_specific_player_data() throws Exception {
        ServiceResult<PlayerData> response = ServiceResult.success(playerData);
        when(dataService.get(playerData.getUsername())).thenReturn(response);
        String resJson = objectMapper.writeValueAsString(response.getData());
        mockMvc.perform(get(path + "/" + playerData.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().string(resJson));
    }

    @Test
    public void should_return_unsuccessful_get_player_data() throws Exception {
        String expectedMsg = "player data could not be recovered";
        ServiceResult<PlayerData> expectedRes = ServiceResult.error(expectedMsg);
        when(dataService.get(playerData.getUsername())).thenReturn(expectedRes);
        mockMvc.perform(get(path + "/" + playerData.getUsername()))
                .andExpect(status().isConflict())
                .andExpect(content().string(expectedRes.getErrorMessage()));
    }

    @Test
    public void should_return_successful_update_message() throws Exception {
        String expectedMsg = "successful update";
        ServiceResult<String> expected = ServiceResult.success(expectedMsg);
        when(dataService.update()).thenReturn(expected);
        mockMvc.perform(post(path))
                .andExpect(status().isOk())
                .andExpect(content().string(expected.getData()));
    }

    @Test
    public void should_return_unsuccessful_update_message() throws Exception {
        String expectedMsg = "could not update the data";
        ServiceResult<String> expectedRes = ServiceResult.error(expectedMsg);
        when(dataService.update()).thenReturn(expectedRes);
        mockMvc.perform(post(path))
                .andExpect(status().isConflict())
                .andExpect(content().string(expectedRes.getErrorMessage()));
    }
}
