package com.spring_webflux_docker.config;

import com.spring_webflux_docker.model.User;
import com.spring_webflux_docker.repository.ReactiveUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(ReactiveUserRepository repository) {
        return args -> {
            repository.deleteAll()
                    .thenMany(Flux.just(
                            new User(null, "Alice", "alice@example.com"),
                            new User(null, "Bob", "bob@example.com")
                    ))
                    .flatMap(repository::save)
                    .subscribe();
        };
    }
}