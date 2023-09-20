package com.facu.altisima.service;

import com.facu.altisima.controller.dto.FinishedGameDto;
import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
import com.facu.altisima.dao.api.GameRepository;
import com.facu.altisima.dao.api.PlayerRepository;
import com.facu.altisima.dao.api.UserRepository;
import com.facu.altisima.model.Game;
import com.facu.altisima.model.Player;
import com.facu.altisima.model.User;
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
    PlayerRepository playerRepository;
    UserRepository userRepository;
    GameRepository gameRepository;
    IdGenerator idGenerator = new FixedIdGenerator("TestId");
    private GameServiceAPI gameService;
    GameGenerator gameGenerator = new GameGenerator();
    private List<String> players;
    Game game;
    Integer totalRounds = 9;
    @BeforeEach
    public void setup() {
        players = new ArrayList<>();
        players.add("Migue");
        game = gameGenerator.generate(players, totalRounds);
        this.gameRepository = mock(GameRepository.class);
        this.playerRepository = mock(PlayerRepository.class);
        this.userRepository = mock(UserRepository.class);
        this.gameService = new GameServiceImpl(gameRepository, playerRepository, userRepository, idGenerator);
    }

    @Test
    public void successfullyCreatedGame() {
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        ServiceResult<Game> returnedGame = gameService.createGame(players, totalRounds);

        assertEquals(game, returnedGame.getData());
    }

    @Test
    public void tooManyPlayersToCreateGame() {
        List<String> exceededPlayersList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            exceededPlayersList.add("Migue" + i);
        }
        String expectedErrMsg = "Demasiados jugadores";

        ServiceResult<Game> returnedGame = gameService.createGame(exceededPlayersList, totalRounds);

        assertEquals(expectedErrMsg, returnedGame.getErrorMessage());
    }

    @Test
    public void successfulGetAllGames() {
        List<Game> games = new ArrayList<>();
        games.add(game);
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
        Optional<Game> successfulOptionalGame = Optional.of(game);
        when(gameRepository.findById(game.getId())).thenReturn(successfulOptionalGame);

        ServiceResult<Game> returnedGame = gameService.getGame(game.getId());

        assertEquals(returnedGame.getData(), game);
    }

    @Test
    public void idGameToGetNotFound() {
        String gameNotFoundMsg = "No se encontro partida con ese id";
        when(gameRepository.findById(game.getId())).thenReturn(Optional.empty());

        ServiceResult<Game> returnedGame = gameService.getGame(game.getId());

        assertEquals(returnedGame.getErrorMessage(), gameNotFoundMsg);
    }

    @Test
    public void successfulDeleteGame() {
        Optional<Game> gameOptional= Optional.of(game);
        when(gameRepository.findById(game.getId())).thenReturn(gameOptional);
        doNothing().when(gameRepository).deleteById(game.getId());
        String successfulDeleteMsg = "Exitosamente borrado";

        ServiceResult<String> result = gameService.delete(game.getId());

        verify(gameRepository, times(1)).deleteById(game.getId());
        assertEquals(result.getData(), successfulDeleteMsg);
    }

    @Test
    public void gameToDeleteDoesNotExist() {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.empty());

        String gameToDeleteNotFoundMsg = "No se encontro la partida";
        ServiceResult<String> result = gameService.delete(game.getId());

        verify(gameRepository, times(0)).deleteById(game.getId());
        assertEquals(result.getErrorMessage(), gameToDeleteNotFoundMsg);
    }

    @Test
    public void successfulNextRound() {
        Optional<Game> gameOptional = Optional.of(game);
        List<PlayerRoundDto> playersRound = gameGenerator.generateRoundBids(players);

        PlayerResultDto expectedPlayerResult = new PlayerResultDto(players.get(0), -1);
        List<PlayerResultDto> expectedResults = new ArrayList<>();
        expectedResults.add(expectedPlayerResult);

        Game expectedGame = gameGenerator.generate(players, totalRounds);
        expectedGame.setLastBidsRound(playersRound);
        expectedGame.setCurrentRound(expectedGame.getCurrentRound() + 1);
        expectedGame.setCurrentResults(expectedResults);

        when(gameRepository.findById(game.getId())).thenReturn(gameOptional);
        when(gameRepository.save(game)).thenReturn(expectedGame);

        ServiceResult<Game> returnedGameState = gameService.nextRound(game.getId(), playersRound);

        verify(gameRepository, times(1)).save(game);
        assertEquals(expectedGame, returnedGameState.getData());
    }

    @Test
    public void idGameToNextRoundNotFound() {
        String expectedErrMsg = "No se encontro la partida";
        when(gameRepository.findById(game.getId())).thenReturn(Optional.empty());
        List<PlayerRoundDto> playersRound = gameGenerator.generateRoundBids(players);

        ServiceResult<Game> gameServiceResult = gameService.nextRound(game.getId(), playersRound);

        assertEquals(expectedErrMsg, gameServiceResult.getErrorMessage());
    }

    @Test
    public void requestedGameToNextRoundIsFinished() {
        String expectedErrMsg = "La partida ya esta terminada";
        game.setCurrentRound(10);
        List<PlayerRoundDto> playersRound = gameGenerator.generateRoundBids(players);
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));

        ServiceResult<Game> gameServiceResult = gameService.nextRound(game.getId(), playersRound);

        assertEquals(expectedErrMsg, gameServiceResult.getErrorMessage());
    }

    @Test
    public void successfulPrevRound() {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));

        ServiceResult<List<PlayerRoundDto>> expectedResult = ServiceResult.success(game.getLastBidsRound());

        assertEquals(expectedResult.getData(), game.getLastBidsRound());
    }

    @Test
    public void idGameToPrevRoundNotFound() {
        String expectedErrMsg = "No se encontro la partida";
        when(gameRepository.findById(game.getId())).thenReturn(Optional.empty());

        ServiceResult<List<PlayerRoundDto>> expectedResult = ServiceResult.error(expectedErrMsg);

        assertEquals(expectedResult.getErrorMessage(), expectedErrMsg);
    }

    @Test
    public void successfulFinishedGame() {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));

        Player player = new Player("23","Batman","www.image.com/batman", 0, 0, 0);
        when(playerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(player));

        User user = new User("43", "Mister", "www.image.com/image", "asdf", 0);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        String expectedMsg = "Se guardaron los datos de la partida";
        FinishedGameDto finishedGameDto = new FinishedGameDto(game.getId(), "Facu", "Migue");

        ServiceResult<String> finishGameServiceResult = gameService.finishGame(finishedGameDto);

        assertEquals(expectedMsg, finishGameServiceResult.getData());
    }

}
