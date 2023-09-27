package com.facu.altisima;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    String dataBaseLink = System.getenv("DATABASE_LINK");
    //"mongodb://localhost:27017"

    public @Bean MongoClient mongoClient() {
        return MongoClients.create(dataBaseLink);
    }
}

