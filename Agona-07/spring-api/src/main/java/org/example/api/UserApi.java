package org.example.api;

import org.example.dto.request.UserRequest;
import org.example.dto.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RequestMapping("/api/v1/users")
public interface UserApi {

    @GetMapping("/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    UserResponse getById(@PathVariable("user-id") UUID uuid);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Set<UserResponse> getAll();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID create(@RequestBody UserRequest userRequest);

    @PutMapping("/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> update(@PathVariable("user-id") UUID uuid, @RequestBody UserRequest userRequest);

    @DeleteMapping("/{user-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> delete(@PathVariable("user-id") UUID uuid);

    @PatchMapping("/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> patch(@PathVariable("user-id") UUID uuid, @RequestBody UserRequest userRequest);
}

