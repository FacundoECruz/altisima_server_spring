package com.facu.altisima.service;

import com.facu.altisima.dao.api.PlayerRepository;
import com.facu.altisima.model.Player;
import com.facu.altisima.service.api.PlayerServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlayerServiceTest {

    @Autowired
    PlayerServiceAPI playerService;

    @MockBean
    PlayerRepository playerRepository;

    Player player = new Player("1", "batman", "www.image.com/batman", 0, 0, 0);

    @Test
    public void getAllPlayers() {
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.add(player);

        when(playerRepository.findAll()).thenReturn(allPlayers);

        ServiceResult<List<Player>> returnedAllPlayersList = playerService.getAll();

        assertEquals(returnedAllPlayersList.getData(), allPlayers);
    }

    @Test
    public void unSuccessfulGetAllPlayers() {
        when(playerRepository.findAll()).thenReturn(null);
        String expectedMsg = "No se pudieron recuperar los jugadores";

        ServiceResult<List<Player>> returnedAllPlayersList = playerService.getAll();

        assertEquals(returnedAllPlayersList.getErrorMessage(), expectedMsg);
    }
}
