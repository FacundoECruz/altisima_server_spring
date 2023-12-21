package com.facu.altisima.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "playerData")
@AllArgsConstructor
@Data
public class PlayerData {
    @Id
    private String _id;
    private String username;
    private Integer totalScore;
    private Integer playedGames;
    private Integer flawlessVictory;
    private Integer playedRounds;
    private Integer winnedRounds;
    private Integer lostRounds;
    private Integer earlyGameScore;
    private Integer midGameScore;
    private Integer lateGameScore;
    private Integer totalExtraScore;
    private Integer bestStreak;
    private Integer worstStreak;
}
