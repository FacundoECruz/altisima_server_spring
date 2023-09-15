package com.facu.altisima.service;

import com.facu.altisima.controller.dto.PlayerResult;
import com.facu.altisima.controller.dto.PlayerRound;
import com.facu.altisima.dao.api.GameRepository;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.api.GameServiceAPI;
import com.facu.altisima.service.impl.GameServiceImpl;

import com.facu.altisima.utils.GameGenerator;
import com.facu.altisima.service.utils.IdGenerator;
import com.facu.altisima.service.utils.ServiceResult;
import com.facu.altisima.utils.FixedIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class GameServiceTest {

    GameRepository gameRepository = mock(GameRepository.class);
    IdGenerator idGenerator = new FixedIdGenerator("TestId");
    private final GameServiceAPI gameService = new GameServiceImpl(gameRepository, idGenerator);
    GameGenerator gameGenerator = new GameGenerator();

    private List<String> players;
    Integer totalRounds = 9;
    @BeforeEach
    public void setup() {
        players = new ArrayList<>();
        players.add("Migue");
    }
    @Test
    public void successfullyCreatedGame() {
        Game game = gameGenerator.generate(players, totalRounds);

        when(gameRepository.save(any(Game.class))).thenReturn(game);

        ServiceResult<Game> returnedGame = gameService.createGame(players, totalRounds);

        assertEquals(game, returnedGame.getData());
    }

    @Test
    public void unsuccessfulCreatedGame() {
        List<String> exceededPlayersList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            exceededPlayersList.add("Migue" + i);
        }
        Integer totalRounds = 9;

        String expectedErrMsg = "Demasiados jugadores";

        ServiceResult<Game> returnedGame = gameService.createGame(exceededPlayersList, totalRounds);

        assertEquals(expectedErrMsg, returnedGame.getErrorMessage());
    }

    @Test
    public void successfulGetAllGames() {
        List<Game> games = new ArrayList<>();
        games.add(gameGenerator.generate(players, totalRounds));

        when(gameRepository.findAll()).thenReturn(games);

        ServiceResult<List<Game>> serviceAllGames = gameService.getAllGames();

        assertEquals(games, serviceAllGames.getData());
    }

    @Test
    public void unsuccessfulGetAllGames() {
        String expectedErrMsg = "No se encontraron partidas";
        List<Game> emptyList = new ArrayList<>();

        when(gameRepository.findAll()).thenReturn(emptyList);

        ServiceResult<List<Game>> serviceAllGames = gameService.getAllGames();

        assertEquals(expectedErrMsg, serviceAllGames.getErrorMessage());
    }

    @Test
    public void successfulGetGame() {
        Game game = gameGenerator.generate(players, totalRounds);
        Optional<Game> successfulOptionalGame = Optional.of(game);
        when(gameRepository.findById(game.getId())).thenReturn(successfulOptionalGame);

        ServiceResult<Game> returnedGame = gameService.getGame(game.getId());

        assertEquals(returnedGame.getData(), game);
    }

    @Test
    public void idGameToGetNotFound() {
        String gameNotFoundMsg = "No se encontro partida con ese id";
        Game game = gameGenerator.generate(players, totalRounds);
        Optional<Game> unsuccessfulOptionalGame = Optional.empty();

        when(gameRepository.findById(game.getId())).thenReturn(unsuccessfulOptionalGame);

        ServiceResult<Game> returnedGame = gameService.getGame(game.getId());

        assertEquals(returnedGame.getErrorMessage(), gameNotFoundMsg);
    }

    @Test
    public void successfulDeleteGame() {
        Game game = gameGenerator.generate(players, totalRounds);
        Optional<Game> gameOptional= Optional.of(game);
        when(gameRepository.findById(game.getId())).thenReturn(gameOptional);
        doNothing().when(gameRepository).deleteById(game.getId());

        String successfulDeleteMsg = "Exitosamente borrado";
        ServiceResult<String> result = gameService.delete(game.getId());

        verify(gameRepository, times(1)).deleteById(game.getId());
        assertEquals(result.getData(), successfulDeleteMsg);
    }

    @Test
    public void unsuccessfulDeleteGame() {
        Game game = gameGenerator.generate(players, totalRounds);
        when(gameRepository.findById(game.getId())).thenReturn(Optional.empty());

        String gameToDeleteNotFoundMsg = "No se encontro la partida";
        ServiceResult<String> result = gameService.delete(game.getId());

        verify(gameRepository, times(0)).deleteById(game.getId());
        assertEquals(result.getErrorMessage(), gameToDeleteNotFoundMsg);
    }

    @Test
    public void successfulNextRound() {
        //Aca estamos testeando el cablerio
        //No estamos probando aun si al que pierde le resta lo que le tiene que restar

        Game game = gameGenerator.generate(players, totalRounds);
        Optional<Game> gameOptional = Optional.of(game);

        List<PlayerRound> playersRound = gameGenerator.generateRoundBids(players);

        game.setCurrentRound(game.getCurrentRound() + 1);
        game.setLastBidsRound(playersRound);
        List<PlayerResult> currentResults = game.getCurrentResults();
        List<PlayerResult> updatedResults = new ArrayList<>();
        for(int i = 0; i < currentResults.size(); i++) {
            PlayerResult playerResult = currentResults.get(i);
            playerResult.setScore(playerResult.getScore() + 5);
            updatedResults.add(playerResult);
        }
        game.setCurrentResults(updatedResults);

        when(gameRepository.findById(game.getId())).thenReturn(gameOptional);
        when(gameRepository.save(game)).thenReturn(game);

        ServiceResult<Game> returnedGameState = gameService.nextRound(game.getId(), playersRound);

        verify(gameRepository, times(1)).save(game);
        assertEquals(game, returnedGameState.getData());
    }

    @Test
    public void idGameToNextRoundNotFound() {
        Game game = gameGenerator.generate(players, totalRounds);
        when(gameRepository.findById(game.getId())).thenReturn(Optional.empty());

        List<PlayerRound> playersRound = gameGenerator.generateRoundBids(players);

        ServiceResult<Game> gameServiceResult = gameService.nextRound(game.getId(), playersRound);
        String expectedErrMsg = "No se encontro la partida";

        assertEquals(expectedErrMsg, gameServiceResult.getErrorMessage());
    }

    @Test
    public void requestedGameToNextRoundIsFinished() {
        Game game = gameGenerator.generate(players, totalRounds);
        game.setCurrentRound(10);
        Optional<Game> gameOptional = Optional.of(game);

        List<PlayerRound> playersRound = gameGenerator.generateRoundBids(players);
        when(gameRepository.findById(game.getId())).thenReturn(gameOptional);

        ServiceResult<Game> gameServiceResult = gameService.nextRound(game.getId(), playersRound);
        String expectedErrMsg = "La partida ya esta terminada";

        assertEquals(expectedErrMsg, gameServiceResult.getErrorMessage());
    }

    @Test
    public void successfulPrevRound() {

    }

    @Test
    public void idGameToPrevRoundNotFound() {

    }

    @Test
    public void requestedGameToPrevRoundIsFinished() {

    }

    @Test
    public void thereIsNoPrevRound() {

    }

    @Test
    public void successfulFinishedGame() {

    }

    @Test
    public void insufficientNumberOfRounds() {

    }

    @Test
    public void gameToFinishIsNotFound() {

    }
}
