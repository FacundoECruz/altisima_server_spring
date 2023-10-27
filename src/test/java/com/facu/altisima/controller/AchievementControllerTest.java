package com.facu.altisima.controller;

import com.facu.altisima.model.AchievementReport;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.impl.AchievementService;
import com.facu.altisima.service.utils.ServiceResult;
import com.facu.altisima.utils.GameGenerator;
import com.facu.altisima.utils.ReportGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
public class AchievementControllerTest {
    @Autowired
    private MockMvc mockMvc;
    public static final int NUMBER_OF_PLAYERS = 4;
    public static final int TOTAL_ROUNDS = 9;
    @MockBean
    AchievementService achievementService;
    GameGenerator gameGenerator = new GameGenerator();
    ReportGenerator reportGenerator = new ReportGenerator();

    ObjectMapper objectMapper = new ObjectMapper();
    List<String> players = new ArrayList<>();
    Game game;
    AchievementReport achievementReport = reportGenerator.generate();
    String achievementReportJson;
    ServiceResult<AchievementReport> reportResult = ServiceResult.success(achievementReport);

    String path = "/achievements";

    @BeforeEach
    public void setup() throws JsonProcessingException {
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            String playerName = "player" + i;
            players.add(playerName);
        }
        game = gameGenerator.generate(players, TOTAL_ROUNDS);
        achievementReportJson = objectMapper.writeValueAsString(achievementReport);
    }

    @Test
    public void should_return_the_first_report() throws Exception {
        when(achievementService.save()).thenReturn(reportResult);

        mockMvc.perform(post(path))
                .andExpect(status().isOk())
                .andExpect(content().string(achievementReportJson));
    }

    @Test
    public void return_error_message_when_service_fails_to_generate_first_report() throws Exception {
        String message = "No se pudo generar el reporte";
        ServiceResult<AchievementReport> serviceFailure = ServiceResult.error(message);
        when(achievementService.save()).thenReturn(serviceFailure);
        mockMvc.perform(post(path))
                .andExpect(status().isConflict())
                .andExpect(content().string(message));
    }

    @Test
    public void should_return_the_current_report() throws Exception {
        when(achievementService.getReport()).thenReturn(reportResult);
        mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(content().string(achievementReportJson));
    }

    @Test
    public void should_return_error_message_if_not_found_report() throws Exception {
        String message = "No se encontro el reporte";
        ServiceResult<AchievementReport> serviceFailure = ServiceResult.error(message);
        when(achievementService.getReport()).thenReturn(serviceFailure);
        mockMvc.perform(get(path))
                .andExpect(status().isConflict())
                .andExpect(content().string(message));
    }

    @Test
    public void should_receive_a_game_and_update_the_report() throws Exception {
        String gameJson = objectMapper.writeValueAsString(game);
        when(achievementService.update(game)).thenReturn(reportResult);
        mockMvc.perform(post(path + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson))
                .andExpect(status().isOk())
                .andExpect(content().string(achievementReportJson));
    }

    @Test
    public void return_error_message_when_updading_fails() throws Exception {
        String gameJson = objectMapper.writeValueAsString(game);
        String message = "No se pudo actualizar el reporte";
        ServiceResult<AchievementReport> serviceFailure = ServiceResult.error(message);
        when(achievementService.update(game)).thenReturn(serviceFailure);
        mockMvc.perform(post(path + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson))
                .andExpect(status().isConflict())
                .andExpect(content().string(message));
    }
}
