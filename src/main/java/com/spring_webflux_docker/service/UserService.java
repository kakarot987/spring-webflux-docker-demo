package com.spring_webflux_docker.service;

import com.spring_webflux_docker.dto.UserDto;
import com.spring_webflux_docker.model.User;
import com.spring_webflux_docker.repository.ReactiveUserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserService {

    private final ReactiveUserRepository userRepository;

    public UserService(ReactiveUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create User
    public Mono<User> createUser(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
        return userRepository.save(user);
    }

    // Get User by ID
    public Mono<User> getUser(String id) {
        return userRepository.findById(UUID.fromString(id));
    }

    // Get All Users
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update User by ID
    public Mono<User> updateUser(String id, UserDto userDto) {
        return userRepository.findById(UUID.fromString(id))
                .flatMap(existingUser -> {
                    existingUser.setName(userDto.getName());
                    existingUser.setEmail(userDto.getEmail());
                    return userRepository.save(existingUser);
                });
    }

    // Delete User by ID
    public Mono<Void> deleteUser(String id) {
        return userRepository.deleteById(UUID.fromString(id));
    }
}
