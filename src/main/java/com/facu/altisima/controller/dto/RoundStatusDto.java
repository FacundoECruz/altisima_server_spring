package com.facu.altisima.controller.dto;

import java.util.Objects;

public class RoundStatusDto {
    Integer current;
    Integer cardsToDeal;

    public RoundStatusDto(Integer current, Integer cardsToDeal) {
        this.current = current;
        this.cardsToDeal = cardsToDeal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoundStatusDto that = (RoundStatusDto) o;
        return Objects.equals(current, that.current) && Objects.equals(cardsToDeal, that.cardsToDeal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(current, cardsToDeal);
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getCardsToDeal() {
        return cardsToDeal;
    }

    public void setCardsToDeal(Integer cardsToDeal) {
        this.cardsToDeal = cardsToDeal;
    }
}
