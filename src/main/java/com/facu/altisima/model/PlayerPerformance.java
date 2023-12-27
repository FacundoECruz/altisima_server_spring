package com.facu.altisima.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
public class PlayerPerformance {
    private String username;
    private Integer score;
    private List<Integer> history;
    private String date;
}
