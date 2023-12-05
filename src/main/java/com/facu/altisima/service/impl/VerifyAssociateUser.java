package com.facu.altisima.service.impl;

import com.facu.altisima.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class VerifyAssociateUser {
    UserRepository userRepository;
    private final Integer graceTimeInDays = 2;
    public boolean isValidLogin(Long createdDate){
        long expiredGraceTime = getExpiredGraceTime(createdDate);
        long now = getNow();
        return expiredGraceTime > now;
    }

    private long getExpiredGraceTime(Long createdDate) {
        Instant createdInstant = Instant.ofEpochMilli(createdDate);
        Instant expiredGraceTimeInstant = createdInstant.plus(graceTimeInDays, ChronoUnit.DAYS);
        return expiredGraceTimeInstant.toEpochMilli();
    }
    private long getNow(){
        LocalDateTime now = LocalDateTime.now();
        Instant nowInstant = now.atZone(ZoneOffset.UTC).toInstant();
        return nowInstant.toEpochMilli();
    }
}
