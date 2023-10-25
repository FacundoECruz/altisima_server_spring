package com.facu.altisima.model;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.repository.PlayerRepository;
import com.facu.altisima.service.utils.ServiceResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Top3Test {

    List<PlayerInTop> prevTop3 = new ArrayList<>();
    PlayerInTop player1 = new PlayerInTop("Messi", 8, 666);
    PlayerInTop player2 = new PlayerInTop("Cristiano", 7, 620);
    PlayerInTop player3 = new PlayerInTop("Mbappe", 6, 610);
    Player fakePlayerFromDb = new Player("fakeId", "Cristiano", "www.image.com", 8, 20, 680);
    List<Integer> fakeHistory = new ArrayList<>();
    PlayerRepository playerRepository;
    List<PlayerResultDto> fakeResults = new ArrayList<>();
    PlayerResultDto fakeResultDto;
    private Top3 top3;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup(){
        prevTop3.add(player1);
        prevTop3.add(player2);
        prevTop3.add(player3);
        fakeResultDto= new PlayerResultDto("Cristiano", 60, fakeHistory);
        fakeResults.add(fakeResultDto);
        this.playerRepository = mock(PlayerRepository.class);
        this.top3 = new Top3(prevTop3, playerRepository);
    }

    @Test
    public void should_update_the_1st_place_of_the_top() {
        PlayerInTop expectedNewTop1 = new PlayerInTop(fakePlayerFromDb.getUsername(),
                fakePlayerFromDb.getGamesWon(), fakePlayerFromDb.getTotalScore());
        when(playerRepository.findByUsername(fakeResultDto.getUsername())).thenReturn(
                Optional.of(fakePlayerFromDb));
        ServiceResult<List<PlayerInTop>> result = top3.check(fakeResults);
        Assertions.assertEquals(result.getData().get(0), expectedNewTop1);
    }

    @Test
    public void should_update_the_2nd_place_in_the_top() throws JsonProcessingException {
        Player fakePlayer = new Player("fakeId", "Isco", "www.image.com", 7, 20, 645);
        PlayerInTop expectedNewTop2 = new PlayerInTop(fakePlayer.getUsername(),
                fakePlayer.getGamesWon(), fakePlayer.getTotalScore());
        List<PlayerResultDto> anotherFakeResults = generateFakeResults(fakePlayer.getUsername());
        when(playerRepository.findByUsername(fakePlayer.getUsername())).thenReturn(
                Optional.of(fakePlayer));
        ServiceResult<List<PlayerInTop>> result = top3.check(anotherFakeResults);
        Assertions.assertEquals(result.getData().get(1), expectedNewTop2);
    }
    @NotNull
    private List<PlayerResultDto> generateFakeResults(String playerName) {
        List<PlayerResultDto> anotherFakeResults = new ArrayList<>();
        PlayerResultDto playerDto = new PlayerResultDto(playerName, 56, fakeHistory);
        anotherFakeResults.add(playerDto);
        return anotherFakeResults;
    }

    @Test
    public void should_update_the_3rd_place_in_the_top() throws JsonProcessingException {
        Player anotherFakePlayer = new Player("fakeId", "Telechea", "www.image.com", 6, 20, 615);
        PlayerInTop expectedNewTop3 = new PlayerInTop(anotherFakePlayer.getUsername(),
                anotherFakePlayer.getGamesWon(), anotherFakePlayer.getTotalScore());
        List<PlayerResultDto> results = generateFakeResults(anotherFakePlayer.getUsername());
        when(playerRepository.findByUsername(anotherFakePlayer.getUsername())).thenReturn(
                Optional.of(anotherFakePlayer));
        ServiceResult<List<PlayerInTop>> result = top3.check(results);
        Assertions.assertEquals(result.getData().get(2), expectedNewTop3);
    }

    @Test
    public void should_not_update_anything(){
        Player toplessPlayer = new Player("fakeId", "Llorente", "www.image.com", 3, 20, 300);
        List<PlayerResultDto> results = generateFakeResults(toplessPlayer.getUsername());
        when(playerRepository.findByUsername(toplessPlayer.getUsername())).thenReturn(Optional.of(toplessPlayer));
        ServiceResult<List<PlayerInTop>> res = top3.check(results);
        Assertions.assertEquals(res.getErrorMessage(), "No hubo cambios en el top 3");
    }
}
