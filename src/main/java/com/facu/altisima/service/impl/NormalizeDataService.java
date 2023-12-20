package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.model.Game;
import com.facu.altisima.model.PlayerData;
import com.facu.altisima.model.PlayerPerformance;
import com.facu.altisima.service.api.NormalizeDataServiceAPI;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NormalizeDataService implements NormalizeDataServiceAPI {
    @Override
    public List<PlayerData> normalizeAndUpdatePlayersData(List<Game> allGames) {
        List<Game> completedGames = filterGames(allGames);
        List<PlayerPerformance> performances = getPerformances(completedGames);
        return groupByPlayer(performances);
    }

    private List<Game> filterGames(List<Game> allGames) {
        List<Game> completedGames = new ArrayList<>();
        for (Game game : allGames) {
            if (game.getCurrentRound() == 10) {
                completedGames.add(game);
            }
        }
        return completedGames;
    }

    private List<PlayerPerformance> getPerformances(List<Game> completedGames) {
        List<PlayerPerformance> performances = new ArrayList<>();
        for (Game game : completedGames) {
            List<PlayerResultDto> results = game.getCurrentResults();
            for (PlayerResultDto result : results) {
                String username = result.getUsername();
                Integer score = result.getScore();
                List<Integer> history = result.getHistory();
                String date = game.getDate();
                PlayerPerformance playerResult = new PlayerPerformance(username, score, history, date);
                performances.add(playerResult);
            }
        }
        return performances;
    }

    private List<PlayerData> groupByPlayer(List<PlayerPerformance> performances) {
        List<PlayerData> data = new ArrayList<>();

        Map<String, List<PlayerPerformance>> groupedByPlayer = performances.stream()
                .collect(Collectors.groupingBy(PlayerPerformance::getUsername));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm");
        groupedByPlayer.forEach((username, dataList) -> {
            List<PlayerPerformance> sortedList = dataList.stream()
                    .sorted(Comparator.comparing(scoreData -> LocalDateTime.parse(scoreData.getDate(), formatter))).toList();
        });

        groupedByPlayer.forEach((username, dataList) -> {
            Integer totalScore = dataList.stream().mapToInt(PlayerPerformance::getScore).sum();
            Integer playedGames = dataList.size();
            Integer flawlessVictory = getFlawlessVictories(dataList);
            Integer winnedRounds = getWinnedRounds(dataList);
            Integer lostRounds = getLostRounds(dataList);
            Integer playedRounds = winnedRounds + lostRounds;
            Integer earlyGameScore = getEarlyGameScore(dataList);
            Integer midGameScore = getMidGameScore(dataList);
            Integer lateGameScore = getLateGameScore(dataList);
            Integer totalExtraScore = getTotalExtraScore(dataList);
            Integer bestStreak = getBestStreak(dataList);
            Integer worstStreak = getWorstStreak(dataList);
            PlayerData playerData = new PlayerData(username, username, totalScore, playedGames, flawlessVictory, playedRounds, winnedRounds, lostRounds, earlyGameScore, midGameScore, lateGameScore, totalExtraScore, bestStreak, worstStreak);
            data.add(playerData);
        });
        return data;
    }

    private Integer getFlawlessVictories(List<PlayerPerformance> dataList) {
        int flawlessVictories = 0;
        for (PlayerPerformance playerGame : dataList) {
            List<Integer> history = playerGame.getHistory();
            boolean flawless = true;
            for (Integer integer : history) {
                if (integer < 0) {
                    flawless = false;
                    break;
                }
            }
            if (flawless) {
                flawlessVictories = flawlessVictories + 1;
            }
        }
        return flawlessVictories;
    }

    private Integer getWinnedRounds(List<PlayerPerformance> dataList) {
        int winnedRounds = 0;
        for (PlayerPerformance playerGame : dataList) {
            List<Integer> history = playerGame.getHistory();
            for (Integer integer : history) {
                if (integer > 0) {
                    winnedRounds = winnedRounds + 1;
                }
            }
        }
        return winnedRounds;
    }

    private Integer getLostRounds(List<PlayerPerformance> dataList) {
        int lostRounds = 0;
        for (PlayerPerformance playerGame : dataList) {
            List<Integer> history = playerGame.getHistory();
            for (Integer integer : history) {
                if (integer < 0) {
                    lostRounds = lostRounds + 1;
                }
            }
        }
        return lostRounds;
    }

    private Integer getEarlyGameScore(List<PlayerPerformance> dataList) {
        int earlyGameScore = 0;
        for (PlayerPerformance playerGame : dataList) {
            List<Integer> history = playerGame.getHistory();
            for (int j = 0; j < 3; j++) {
                earlyGameScore = earlyGameScore + history.get(j);
            }
        }
        return earlyGameScore;
    }

    private Integer getMidGameScore(List<PlayerPerformance> dataList) {
        int midGameScore = 0;
        for (PlayerPerformance playerGame : dataList) {
            List<Integer> history = playerGame.getHistory();
            for (int j = 3; j < 6; j++) {
                midGameScore = midGameScore + history.get(j);
            }
        }
        return midGameScore;
    }

    private Integer getLateGameScore(List<PlayerPerformance> dataList) {
        int lateGameScore = 0;
        for (PlayerPerformance playerGame : dataList) {
            List<Integer> history = playerGame.getHistory();
            for (int j = 6; j < 9; j++) {
                lateGameScore = lateGameScore + history.get(j);
            }
        }
        return lateGameScore;
    }

    private Integer getTotalExtraScore(List<PlayerPerformance> dataList) {
        int totalExtraScore = 0;
        for (PlayerPerformance playerGame : dataList) {
            List<Integer> history = playerGame.getHistory();
            for (Integer integer : history) {
                if (integer > 5) {
                    totalExtraScore = totalExtraScore + (integer - 5);
                }
            }
        }
        return totalExtraScore;
    }

    private Integer getBestStreak(List<PlayerPerformance> dataList) {
        int maxStreak = 0;
        int currentStreak = 0;
        for (PlayerPerformance playerPerformance : dataList) {
            List<Integer> history = playerPerformance.getHistory();
            for (Integer integer : history) {
                if (integer > 0) {
                    currentStreak = currentStreak + 1;
                    maxStreak = Math.max(maxStreak, currentStreak);
                } else {
                    currentStreak = 0;
                }
            }
        }
        return maxStreak;
    }

    private Integer getWorstStreak(List<PlayerPerformance> dataList) {
        int maxStreak = 0;
        int currentStreak = 0;
        for (PlayerPerformance playerPerformance : dataList) {
            List<Integer> history = playerPerformance.getHistory();
            for (Integer integer : history) {
                if (integer < 0) {
                    currentStreak = currentStreak + 1;
                    maxStreak = Math.max(maxStreak, currentStreak);
                } else {
                    currentStreak = 0;
                }
            }
        }
        return maxStreak;
    }
}
