package com.facu.altisima.model;

import com.facu.altisima.utils.ReportGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AchievementReportTest {
    List<Score> topScoreInAGame = new ArrayList<>();
    List<Score> wasTopScoreInAGame = new ArrayList<>();
    AchievementReport achievementReport;

    ObjectMapper objectMapper = new ObjectMapper();

    ReportGenerator reportGenerator = new ReportGenerator();

    @BeforeEach
    public void setup(){
        achievementReport = reportGenerator.generate();
    }

}
