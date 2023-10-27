package com.facu.altisima.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class PlayerInTop implements Comparable<PlayerInTop>{
    @JsonProperty("username")
    private String username;
    @JsonProperty("gamesWon")
    private Integer gamesWon;
    @JsonProperty("totalScore")
    private Integer totalScore;

    public PlayerInTop(String username, Integer gamesWon, Integer totalScore) {
        this.username = username;
        this.gamesWon = gamesWon;
        this.totalScore = totalScore;
    }

    @Override
    public int compareTo(PlayerInTop other) {
        // Implementa la lógica de comparación
        if (this.gamesWon > other.gamesWon) {
            return 1; // Ordenar de mayor a menor
        } else if (this.gamesWon < other.gamesWon) {
            return -1;
        } else {
            // Si el número de partidas es igual, compara por puntaje total
            if (this.totalScore > other.totalScore) {
                return 1;
            } else if (this.totalScore < other.totalScore) {
                return -1;
            } else {
                // Si el puntaje total es igual, compara por nombre de usuario
                return this.username.compareTo(other.username);
            }
        }
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerInTop that = (PlayerInTop) o;
        return Objects.equals(username, that.username) && Objects.equals(gamesWon, that.gamesWon) && Objects.equals(totalScore, that.totalScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, gamesWon, totalScore);
    }
}
