package com.facu.altisima.service;

import com.facu.altisima.repository.PlayerRepository;
import com.facu.altisima.model.Player;
import com.facu.altisima.service.api.PlayerServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import com.facu.altisima.utils.FixedIdGenerator;
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
public class PlayerServiceTest {

    @Autowired
    PlayerServiceAPI playerService;

    @MockBean
    PlayerRepository playerRepository;

    FixedIdGenerator idGenerator = new FixedIdGenerator("someFakeId");
    Player player = new Player(idGenerator.generate(), "Facu", "www.image.com/image", 0, 0, 0);

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
        List<Player> voidPlayersList = new ArrayList<>();
        when(playerRepository.findAll()).thenReturn(voidPlayersList);
        String expectedMsg = "No se pudieron recuperar los jugadores";

        ServiceResult<List<Player>> returnedAllPlayersList = playerService.getAll();

        assertEquals(returnedAllPlayersList.getErrorMessage(), expectedMsg);
    }

    @Test
    public void successfulSavePlayer() {
        when(playerRepository.findByUsername(player.getUsername())).thenReturn(Optional.empty());
        when(playerRepository.save(player)).thenReturn(player);

        ServiceResult<Player> savedPlayer = playerService.save(player);

        assertEquals(player, savedPlayer.getData());
    }

    @Test
    public void usernameAlreadyExists() {
        when(playerRepository.findByUsername(player.getUsername()))
                .thenReturn(Optional.of(player));
        String expectedMsg = "El nombre de usuario ya existe";

        ServiceResult<Player> returnedPlayer = playerService.save(player);

        assertEquals(expectedMsg, returnedPlayer.getErrorMessage());
    }

    @Test
    public void successfulGetPlayerById() {
        when(playerRepository.findByUsername(player.getUsername())).thenReturn(Optional.of(player));

        ServiceResult<Player> returnedPlayer = playerService.get(player.getUsername());

        assertEquals(player, returnedPlayer.getData());
    }

    @Test
    public void playerDoesNotExist() {
        String expectedMsg = "El nombre de usuario no existe";
        when(playerRepository.findByUsername(player.getUsername())).thenReturn(Optional.empty());

        ServiceResult<Player> returnedPlayer = playerService.get(player.getUsername());

        assertEquals(expectedMsg, returnedPlayer.getErrorMessage());
    }
}
