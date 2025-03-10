package com.spring_webflux_docker.repository;

import com.spring_webflux_docker.model.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface ReactiveUserRepository extends R2dbcRepository<User, UUID> {
}
