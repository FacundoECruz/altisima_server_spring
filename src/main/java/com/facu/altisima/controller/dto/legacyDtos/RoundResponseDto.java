package com.facu.altisima.controller.dto.legacyDtos;

import com.facu.altisima.model.Game;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoundResponseDto {
    private Integer round;
    private List<PlayerRoundWithHistory> newRoundState;
    private String status;

    public RoundResponseDto(Integer round, List<PlayerRoundWithHistory> newRoundState, String status) {
        this.round = round;
        this.newRoundState = newRoundState;
        this.status = status;
    }

    public RoundResponseDto() {

    }
    public RoundResponseDto generate(Game game) {
        Integer round = game.getCurrentRound();
        List<PlayerRoundWithHistory> newRoundState = generateNewRoundState(game);
        String status = "in progress";

        return new RoundResponseDto(round, newRoundState, status);
    }

    @NotNull
    private static List<PlayerRoundWithHistory> generateNewRoundState(Game game) {
        List<PlayerRoundWithHistory> newRoundState = new ArrayList<>();
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerRoundWithHistory player = new PlayerRoundWithHistory(
                    game.getCurrentResults().get(i).getUsername(),
                    game.getCurrentResults().get(i).getScore(),
                    game.getLastBidsRound().get(i).getBid(),
                    game.getLastBidsRound().get(i).getBidsLost(),
                    game.getPlayersImgs().get(i),
                    game.getCurrentResults().get(i).getHistory()
            );
            newRoundState.add(player);
        }
        return newRoundState;
    }


    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public List<PlayerRoundWithHistory> getNewRoundState() {
        return newRoundState;
    }

    public void setNewRoundState(List<PlayerRoundWithHistory> newRoundState) {
        this.newRoundState = newRoundState;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoundResponseDto that = (RoundResponseDto) o;
        return Objects.equals(round, that.round) && Objects.equals(newRoundState, that.newRoundState) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(round, newRoundState, status);
    }
}
