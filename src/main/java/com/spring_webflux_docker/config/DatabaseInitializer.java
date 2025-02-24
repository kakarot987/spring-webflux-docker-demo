package com.spring_webflux_docker.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
@Profile("local")  // Runs only in local profile
public class DatabaseInitializer {

    private final DatabaseClient databaseClient;

    @Value("${spring.app.database.name}")
    private String databaseName;

    public DatabaseInitializer(ConnectionFactory connectionFactory) {
        this.databaseClient = DatabaseClient.create(connectionFactory);
    }

    @PostConstruct
    public void createDatabaseIfNotExists() {
        String query = "SELECT 1 FROM pg_database WHERE datname = '" + databaseName + "'";

        databaseClient.sql(query)
                .fetch()
                .one() // Returns Mono<Map<String, Object>>
                .switchIfEmpty(
                        databaseClient.sql("CREATE DATABASE " + databaseName)
                                .fetch()
                                .one() // ✅ Matches expected Mono<Map<String, Object>>
                )
                .doOnSuccess(result -> System.out.println("✅ Checked and created database if needed: " + databaseName))
                .subscribe();
    }

}

