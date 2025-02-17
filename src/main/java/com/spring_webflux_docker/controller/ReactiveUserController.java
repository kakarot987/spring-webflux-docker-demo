package com.spring_webflux_docker.controller;

import com.spring_webflux_docker.dto.UserDto;
import com.spring_webflux_docker.model.User;
import com.spring_webflux_docker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class ReactiveUserController {

    private final UserService userService;

    public ReactiveUserController(UserService userService) {
        this.userService = userService;
    }

    // Create User
    @PostMapping
    public Mono<User> createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    // Get User by ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUser(@PathVariable String id) {
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Get All Users
    @GetMapping
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Update User by ID
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable String id, @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto)
                .map(updatedUser -> ResponseEntity.ok(updatedUser))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Delete User by ID
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
