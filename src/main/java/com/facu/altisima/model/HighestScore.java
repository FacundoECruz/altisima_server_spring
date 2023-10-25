package com.facu.altisima.model;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.service.utils.ServiceResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HighestScore {
    List<Score> currentHighestScoreInAGame;
    boolean hadChanges;

    public HighestScore(List<Score> currentHighestScoreInAGame) {
        this.currentHighestScoreInAGame = currentHighestScoreInAGame;
    }

    public ServiceResult<List<Score>> check(List<PlayerResultDto> gameResults) {
        PlayerResultDto highScoreInCurrentGame = getHighScoreInGame(gameResults);
        checkIfRecordChanges(highScoreInCurrentGame);
        if (hadChanges)
            return ServiceResult.success(currentHighestScoreInAGame);
        else
            return ServiceResult.error("No hubo cambios en el record actual");
    }
    @NotNull
    private static PlayerResultDto getHighScoreInGame(List<PlayerResultDto> results) {
        List<Integer> fakeHistory = new ArrayList<>();
        PlayerResultDto highScoreInGame = new PlayerResultDto("void", 0, fakeHistory);
        for (PlayerResultDto result : results) {
            if (result.getScore() > highScoreInGame.getScore())
                highScoreInGame = result;
        }
        return highScoreInGame;
    }
    private void checkIfRecordChanges(PlayerResultDto highScoreInCurrentGame) {
        if (isGreaterThanCurrentRecord(highScoreInCurrentGame)) {
            updateCurrentRecord(highScoreInCurrentGame);
        } else if (isEqualToCurrentRecord(highScoreInCurrentGame)) {
            addNewRecordToList(highScoreInCurrentGame);
        }
    }
    private boolean isGreaterThanCurrentRecord(PlayerResultDto highScoreInCurrentGame) {
        return highScoreInCurrentGame.getScore() >
                currentHighestScoreInAGame.get(0).getScore();
    }

    private void updateCurrentRecord(PlayerResultDto highScoreInCurrentGame) {
        List<Score> container = new ArrayList<>();
        Score newRecord = new Score(
                highScoreInCurrentGame.getUsername(),
                highScoreInCurrentGame.getScore());
        container.add(newRecord);
        currentHighestScoreInAGame = container;
        hadChanges = true;
    }
    private boolean isEqualToCurrentRecord(PlayerResultDto highScoreInCurrentGame) {
        return Objects.equals(highScoreInCurrentGame.getScore(),
                currentHighestScoreInAGame.get(0).getScore());
    }

    private void addNewRecordToList(PlayerResultDto highScoreInCurrentGame) {
        Score playerWithRecord = new Score(highScoreInCurrentGame.getUsername(),
                highScoreInCurrentGame.getScore());
        currentHighestScoreInAGame.add(playerWithRecord);
        hadChanges = true;
    }
}
