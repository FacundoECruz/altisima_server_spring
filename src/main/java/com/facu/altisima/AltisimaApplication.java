package com.facu.altisima;

import com.facu.altisima.repository.*;
import com.facu.altisima.service.api.GameServiceAPI;
import com.facu.altisima.service.impl.GameServiceImpl;
import com.facu.altisima.service.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class AltisimaApplication {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(AltisimaApplication.class);
        log.info("Starting Altisima Application");
        SpringApplication.run(AltisimaApplication.class, args);
    }
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }

    @Bean
    public GameRepository gameRepository(@Autowired MongoLegacyGameRepository legacyRepository){
        return new LegacyGameRepository(legacyRepository);
    }

    public GameServiceAPI gameService(
            @Autowired GameRepository gameRepository,
            @Autowired PlayerRepository playerRepository,
            @Autowired UserRepository userRepository,
            @Autowired IdGenerator idGenerator,
            @Autowired AchievementRepository achievementRepository){
        return new GameServiceImpl(gameRepository, playerRepository, userRepository, idGenerator, achievementRepository);
    }
}
